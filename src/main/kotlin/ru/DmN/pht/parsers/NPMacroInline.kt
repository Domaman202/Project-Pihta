package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeMacroUtil
import ru.DmN.pht.parser.utils.macros
import ru.DmN.pht.utils.node.NodeTypes.MACRO_INLINE
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.node.INodeInfo

object NPMacroInline : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPProgn.parse(parser, ctx) { NodeMacroUtil(INodeInfo.of(MACRO_INLINE, ctx, token), it, ctx.macros.reversed()) }
}