package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeMacroUtil
import ru.DmN.pht.node.NodeTypes
import ru.DmN.pht.parser.utils.macros
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn

object NPMacroArg : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPProgn.parse(parser, ctx) { NodeMacroUtil(INodeInfo.of(NodeTypes.MACRO_ARG, ctx, token), it, ctx.macros.reversed()) }
}