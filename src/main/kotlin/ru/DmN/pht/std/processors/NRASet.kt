package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeASet
import ru.DmN.pht.std.utils.line
import ru.DmN.pht.std.utils.processNodes

object NRASet : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[2], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeASet {
        val nodes = processor.processNodes(node, ctx, ValType.VALUE)
        return NodeASet(Token.operation(node.line, "!aset"), nodes[0], nodes[1], nodes[2])
    }
}