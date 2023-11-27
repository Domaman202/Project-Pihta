package ru.DmN.pht.std.processors

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.VirtualType

interface INodeUniversalProcessor<A : Node, B : Node> : INodeParser, INodeUnparser<A>, INodeProcessor<B> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        throw UnsupportedOperationException("Not yet implemented")

    override fun unparse(node: A, unparser: Unparser, ctx: UnparsingContext, indent: Int): Unit =
        throw UnsupportedOperationException("Not yet implemented")

    override fun calc(node: B, processor: Processor, ctx: ProcessingContext): VirtualType? =
        null

    override fun process(node: B, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        node
}