package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.std.ast.NodeIncDec
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.line

object NRIncDec : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeIncDec = // todo: no-int inc/dec
        NodeIncDec(
            node.token.processed(),
            when (node.token.text) {
                "++" -> NodeIncDec.Type.INC
                "--" -> NodeIncDec.Type.DEC
                else -> throw RuntimeException()
            },
            processor.computeString(node.nodes[0], ctx)
        )
}