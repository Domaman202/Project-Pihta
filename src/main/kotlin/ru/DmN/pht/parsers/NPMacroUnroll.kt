package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeMacroUtil
import ru.DmN.pht.parser.utils.macros
import ru.DmN.pht.utils.node.NodeTypes.MACRO_UNROLL
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.node.INodeInfo
import java.util.*

object NPMacroUnroll : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val uuid = UUID.randomUUID()
        ctx.macros.push(uuid)
        return NPProgn.parse(parser, ctx) {
            NodeMacroUtil(INodeInfo.of(MACRO_UNROLL, ctx, token), it, ctx.macros.reversed()).apply {
                ctx.macros.pop()
            }
        }
    }
}