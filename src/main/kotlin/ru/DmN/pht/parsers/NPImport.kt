package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeImport
import ru.DmN.pht.imports.Helper
import ru.DmN.pht.helper.ast.IValueNode
import ru.DmN.pht.node.NodeTypes
import ru.DmN.pht.utils.text
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.nextOperation

object NPImport : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val module = parser.nextOperation().text!!
        val context = ctx.subCtx()
        context.loadedModules.add(0, Helper)
        return NPProgn.parse(parser, context) { it ->
            val map = HashMap<String, MutableList<Any?>>()
            it.forEach { map.getOrPut(it.text) { ArrayList() } += (it as IValueNode).value }
            NodeImport(INodeInfo.of(NodeTypes.IMPORT, ctx, token), module, map)
        }
    }
}