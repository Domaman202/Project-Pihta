package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeMacro
import ru.DmN.pht.utils.node.NodeTypes.MACRO_B
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.node.INodeInfo

object NPMacroB : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPProgn.parse(parser, ctx) { NodeMacro(INodeInfo.of(MACRO_B, ctx, token), it, token.text!!) }
}