package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.IAdaptableNode
import ru.DmN.pht.std.ast.NodeFGet
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.ast.NodeMCall.Type.*
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.node.processed
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.*
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VTDynamic
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

object NRMCall : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType {
        val fourfold = findMethod(node, processor, ctx)
        val instance =
            if (fourfold.first == SUPER)
                nodeGetOrName(node.info, "this")
            else processor.process(node.nodes[0], ctx, ValType.VALUE)!!
        return fourfold.fourth ?: processor.calc(instance, ctx).let { if (it is VTWG) it.gens[0] else fourfold.third.rettype }
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeMCall {
        val info = node.info
        val fourfold = findMethod(node, processor, ctx)
        val instance =
            if (fourfold.first == SUPER)
                nodeGetOrName(info, "this")
            else processor.process(node.nodes[0], ctx, ValType.VALUE)!!
        val generics = fourfold.fourth ?: processor.calc(instance, ctx).let { if (it is VTWG) it.gens[0] else null }
        return if (fourfold.third.extension == null)
            NodeMCall(
                info.processed,
                processArguments(info, processor, ctx, fourfold.third, fourfold.second),
                generics,
                if (fourfold.first == VIRTUAL)
                    NodeFGet(
                        info.withType(NodeTypes.FGET_),
                        mutableListOf(nodeValueClass(info, fourfold.third.declaringClass!!.name)),
                        "INSTANCE",
                        NodeFGet.Type.STATIC,
                        processor.computeType(node.nodes[0], ctx)
                    )
                else instance,
                fourfold.third,
                when (fourfold.first) {
                    UNKNOWN ->
                        if (fourfold.third.modifiers.static)
                            STATIC
                        else VIRTUAL
                    else -> fourfold.first
                }
            )
        else NodeMCall(
            node.info.processed,
            processArguments(
                node.info,
                processor,
                ctx,
                fourfold.third,
                listOf(instance) + fourfold.second
            ),
            generics,
            NodeValue.of(node.info, NodeValue.Type.CLASS, fourfold.third.extension!!.name),
            fourfold.third,
            NodeMCall.Type.EXTEND
        )
    }

    fun processArguments(info: INodeInfo, processor: Processor, ctx: ProcessingContext, method: VirtualMethod, args: List<Node>): MutableList<Node> =
        (if (method.modifiers.varargs) {
            val overflow = args.size.let { if (it > 0) it + 1 else 0 } - method.argsc.size
            if (overflow > 0)
                args.dropLast(overflow).toMutableList().apply {
                    val type = method.argsc.last().componentType!!.name
                    add(
                        NRArrayOfType.process(
                            nodeArrayType(info, type, args.asSequence().drop(args.size - overflow).map { nodeAs(info, it, type) }.toMutableList()),
                            processor,
                            ctx,
                            ValType.VALUE
                        )!!
                    )
                }
            else (args + NRNewArray.process(
                nodeNewArray(info, method.argsc.last().name.substring(1), 0),
                processor,
                ctx,
                ValType.VALUE
            )!!).toMutableList()
        } else args)
            .mapIndexed { i, it -> NRAs.process(nodeAs(info, it, method.argsc[i].name), processor, ctx, ValType.VALUE)!! }
            .toMutableList()

    private fun findMethod(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): Fourfold<NodeMCall.Type, List<Node>, VirtualMethod, VirtualType?> {
        val gctx = ctx.global
        //
        lateinit var clazz: VirtualType
        val type = node.nodes[0].let {
            if (it.isConstClass) {
                clazz = gctx.getType(it.valueAsString, processor.tp)
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
        var generic: VirtualType? = null
        val name = processor.computeString(node.nodes[1], ctx).let {
            val gs = it.indexOf('<')
            if (gs < 1)
                return@let it
            generic = gctx.getType(it.substring(gs + 2, it.length - 1), processor.tp)
            it.substring(0, gs)
        }
        val result = findMethodOrNull(
            clazz,
            name,
            node.nodes.asSequence().drop(2).map { processor.process(it, ctx, ValType.VALUE)!! }.toList(),
            processor,
            ctx
        ) ?: if (clazz == VTDynamic)
            findMethod(
                gctx.getType("ru.DmN.pht.std.utils.DynamicUtils", processor.tp),
                "invokeMethod",
                node.nodes.map { processor.process(it, ctx, ValType.VALUE)!! },
                processor,
                ctx
            )
        else throw RuntimeException("Method '$name' not founded!")
        return Fourfold(
            if (type == STATIC)
                if (result.second.modifiers.static)
                    STATIC
                else VIRTUAL
            else type,
            result.first,
            result.second,
            generic
        )
    }

    fun findMethod(clazz: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): Pair<List<Node>, VirtualMethod> =
        findMethodOrNull(clazz, name, args, processor, ctx) ?: throw RuntimeException("Method '$name' not founded!")

    fun findMethodOrNull(clazz: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): Pair<List<Node>, VirtualMethod>? {
        val method = ctx.global.getMethodVariants(clazz, name, args.map { ICastable.of(it, processor, ctx) }.toList()).firstOrNull() ?: return null
        return Pair(args.mapIndexed { i, it -> if (it is IAdaptableNode) it.adaptTo(method.argsc[i]); it }.toList(), method)
    }
}