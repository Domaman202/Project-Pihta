package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeDefMacro
import ru.DmN.pht.parser.utils.macros
import ru.DmN.pht.utils.node.NodeTypes.DEF_MACRO
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.node.INodeInfo
import java.util.*

object NPDefMacro : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val uuid = UUID.randomUUID()
        ctx.macros.push(uuid)
        return NPProgn.parse(parser, ctx) {
            ctx.macros.pop()
            NodeDefMacro(INodeInfo.of(DEF_MACRO, ctx, token), it, uuid)
        }
    }
}