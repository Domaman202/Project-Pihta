package ru.DmN.pht.std.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.ast.IAdaptableNode
import ru.DmN.pht.std.ast.NodeFGet
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.ast.NodeMCall.Type.*
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.*

object NRMCall : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        if (node is NodeMCall) node.method.rettype else findMethod(node, processor, ctx).third.rettype

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeMCall {
        val triple = findMethod(node, processor, ctx)
        val instance =
            if (triple.first == SUPER)
                nodeGetOrName(node.line, "this")
            else processor.process(node.nodes[0], ctx, ValType.VALUE)!!
        return if (triple.third.extend == null)
            NodeMCall(
                node.token.processed(),
                processArguments(node.line, processor, ctx, triple.third, triple.second),
                if (triple.first == VIRTUAL)
                    NodeFGet(
                        Token.operation(node.line, "!fget"),
                        mutableListOf(nodeClass(node.line, triple.third.declaringClass!!.name)),
                        "INSTANCE",
                        NodeFGet.Type.STATIC,
                        ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp)
                    )
                else instance,
                triple.third,
                when (triple.first) {
                    UNKNOWN ->
                        if (triple.third.modifiers.static)
                            STATIC
                        else VIRTUAL
                    else -> triple.first
                }
            )
        else NodeMCall(
            node.token.processed(),
            processArguments(
                node.line,
                processor,
                ctx,
                triple.third,
                listOf(instance) + triple.second
            ),
            NodeValue.of(node.token.line, NodeValue.Type.CLASS, triple.third.extend!!.name),
            triple.third,
            NodeMCall.Type.EXTEND
        )
    }

    fun processArguments(line: Int, processor: Processor, ctx: ProcessingContext, method: VirtualMethod, args: List<Node>): MutableList<Node> =
        (if (method.modifiers.varargs) {
            val overflow = args.size.let { if (it > 0) it + 1 else 0 } - method.argsc.size
            if (overflow > 0)
                args.dropLast(overflow).toMutableList().apply {
                    add(
                        NRArrayOf.process(
                            nodeArray(line, args.drop(args.size - overflow).toMutableList()),
                            processor,
                            ctx,
                            ValType.VALUE
                        )!!
                    )
                }
            else (args + NRNewArray.process(
                nodeNewArray(line, method.argsc.last().name.substring(1), 0),
                processor,
                ctx,
                ValType.VALUE
            )!!).toMutableList()
        } else args)
            .mapIndexed { i, it -> NRAs.process(nodeAs(line, it, method.argsc[i].name), processor, ctx, ValType.VALUE)!! }
            .toMutableList()

    private fun findMethod(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): Triple<NodeMCall.Type, List<Node>, VirtualMethod> {
        lateinit var clazz: VirtualType
        val type = node.nodes[0].let {
            if (it.isConstClass) {
                clazz = ctx.global.getType(it.valueAsString, processor.tp)
                STATIC
            } else {
                if (it.isLiteral) {
                    when (processor.computeString(it, ctx)) {
                        "." -> {
                            clazz = ctx.clazz
                            return@let UNKNOWN
                        }

                        "this" -> {
                            clazz = processor.calc(it, ctx)!!
                            return@let VIRTUAL
                        }

                        "super" -> {
                            clazz = ctx.clazz.superclass!!
                            return@let SUPER
                        }
                    }
                }

                clazz = processor.calc(it, ctx)!!
                UNKNOWN
            }
        }
        val result = if (clazz == VTDynamic)
            findMethod(
                ctx.global.getType("ru.DmN.pht.std.utils.DynamicUtils", processor.tp),
                "invokeMethod",
                node.nodes.map { processor.process(it, ctx, ValType.VALUE)!! },
                processor,
                ctx
            )
        else findMethod(
            clazz,
            processor.computeString(node.nodes[1], ctx),
            node.nodes.drop(2).map { processor.process(it, ctx, ValType.VALUE)!! },
            processor,
            ctx
        )
        return Triple(
            if (type == STATIC)
                if (result.second.modifiers.static)
                    STATIC
                else VIRTUAL
            else type,
            result.first,
            result.second
        )
    }

    fun findMethod(clazz: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): Pair<List<Node>, VirtualMethod> =
        findMethodOrNull(clazz, name, args, processor, ctx) ?: throw RuntimeException("Method '$name' not founded!")

    fun findMethodOrNull(clazz: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): Pair<List<Node>, VirtualMethod>? {
        val method = ctx.global.getMethodVariants(clazz, name, args.map { ICastable.of(it, processor, ctx) }.toList()).firstOrNull() ?: return null
        return Pair(args.mapIndexed { i, it -> if (it is IAdaptableNode) it.adaptTo(method.argsc[i]); it }.toList(), method)
    }
}