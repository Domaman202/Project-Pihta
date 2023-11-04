package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.IAdaptableNode
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.unparsers.NUDefaultX
import ru.DmN.pht.std.utils.line
import ru.DmN.pht.std.utils.processNodes
import ru.DmN.pht.std.utils.text

object NRMath : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType {
        val firstType = processor.calc(node.nodes[0], ctx)!!
        return findExtend(firstType, NUDefaultX.text(node.token), node.nodes.drop(1), processor, ctx)?.rettype ?: firstType
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val nodes = processor.processNodes(node, ctx, ValType.VALUE)
        val firstType = processor.calc(nodes[0], ctx)!!
        val result = getExtend(firstType, node.token.text!!, nodes.drop(1), processor, ctx)
        val line = node.line
        return if (result == null)
            if (mode == ValType.VALUE)
                NodeNodesList(
                    node.token.processed(),
                    (if (node.text.startsWith("shift")) nodes else nodes.map { NRAs.process(nodeAs(line, it, firstType.name), processor, ctx, ValType.VALUE)!! })
                        .toMutableList(),
                )
            else null
        else NodeMCall(
            Token.operation(line, "!mcall"),
            NRMCall.processArguments(line, processor, ctx, result.second, listOf(nodes[0]) + result.first),
            nodeClass(line, result.second.declaringClass!!.name),
            result.second,
            NodeMCall.Type.EXTEND
        )
    }

    fun getExtend(type: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): Pair<List<Node>, VirtualMethod>? {
        val method = findExtend(type, name, args, processor, ctx) ?: return null
        return Pair(args.mapIndexed { i, it -> if (it is IAdaptableNode) it.adaptTo(method.argsc[i]); it }.toList(), method)
    }

    fun findExtend(type: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): VirtualMethod? =
        ctx.global.getMethodVariants(type, name, args.map { ICastable.of(it, processor, ctx) }.toList()).firstOrNull()
}