package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.INodeParser
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.INodeUnparser
import ru.DmN.pht.base.utils.VirtualType

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