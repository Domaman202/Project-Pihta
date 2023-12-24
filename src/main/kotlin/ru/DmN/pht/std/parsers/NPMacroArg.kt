package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.ast.NodeMacroArg
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.parser.utils.macros
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.NodeInfoImpl
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn

object NPMacroArg : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPProgn.parse(parser, ctx) { NodeMacroArg(NodeInfoImpl.of(NodeTypes.MACRO_ARG, ctx, token), it, ctx.macros.reversed()) }
}