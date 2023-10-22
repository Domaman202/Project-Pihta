package ru.DmN.pht.base.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.processors.INodeUniversalProcessor

object NUPTest : INodeUniversalProcessor<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        Node(operationToken)

    override fun unparse(node: Node, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append("(test)")
    }

    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        node
}