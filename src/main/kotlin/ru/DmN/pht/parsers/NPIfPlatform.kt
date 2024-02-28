package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeIfPlatform
import ru.DmN.pht.utils.node.NodeTypes.CT_IF_PLATFORM
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.Token.DefaultType.OPERATION
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.node.INodeInfo

object NPIfPlatform : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val names = ArrayList<String>()
        var tk = parser.nextToken()!!
        while (tk.type == OPERATION) {
            names.add(tk.text!!.uppercase())
            tk = parser.nextToken()!!
        }
        parser.tokens.push(tk)
        return NPProgn.parse(parser, ctx) { NodeIfPlatform(INodeInfo.of(CT_IF_PLATFORM, ctx, token), it, names) }
    }
}