package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.ast.NodeNewArray

object NUPNewArrayX : INodeUniversalProcessor<NodeNewArray, NodeNewArray> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Nothing =
        throw UnsupportedOperationException("Not yet implemented")

    override fun unparse(node: NodeNewArray, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.run {
            append('(').append("new-array")
                .append('\n').append("\t".repeat(indent + 1)).append(NUPValueA.unparseType(node.type.name))
                .append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.size, ctx, indent + 1)
            append(')')
        }
    }

    override fun calc(node: NodeNewArray, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type
}