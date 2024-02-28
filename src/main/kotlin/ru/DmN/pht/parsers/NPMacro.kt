package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeMacro
import ru.DmN.pht.utils.node.NodeTypes.MACRO
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.nextOperation
import ru.DmN.siberia.utils.node.INodeInfo

object NPMacro : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val name = parser.nextOperation().text!!
        return NPProgn.parse(parser, ctx) { NodeMacro(INodeInfo.of(MACRO, ctx, token), it, name) }
    }
}