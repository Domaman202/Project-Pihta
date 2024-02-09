package ru.DmN.phtx.ppl.parsers

import ru.DmN.phtx.ppl.PageHelper
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.node.INodeType
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn

class NPPage(val type: INodeType) : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val context = ctx.subCtx()
        context.loadedModules.add(0, PageHelper)
        return NPProgn.parse(parser, context) { NodeNodesList(INodeInfo.of(type, context, token), it) }
    }
}