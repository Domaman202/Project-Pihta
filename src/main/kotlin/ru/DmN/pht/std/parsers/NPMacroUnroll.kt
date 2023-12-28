package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.ast.NodeMacroUnroll
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.parser.utils.macros
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import java.util.*

object NPMacroUnroll : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val uuid = UUID.randomUUID()
        ctx.macros.push(uuid)
        return NPProgn.parse(parser, ctx) {
            NodeMacroUnroll(INodeInfo.of(NodeTypes.MACRO_UNROLL, ctx, token), it, ctx.macros.reversed()).apply {
                ctx.macros.pop()
            }
        }
    }
}