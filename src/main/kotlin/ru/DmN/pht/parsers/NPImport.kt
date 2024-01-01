package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.ast.NodeImport
import ru.DmN.pht.std.imports.StdImportsHelper
import ru.DmN.pht.std.imports.ast.IValueNode
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.utils.text
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
        context.loadedModules.add(0, StdImportsHelper)
        return NPProgn.parse(parser, context) { it ->
            val map = HashMap<String, MutableList<Any?>>()
            it.forEach { map.getOrPut(it.text) { ArrayList() } += (it as IValueNode).value }
            NodeImport(INodeInfo.of(NodeTypes.IMPORT, ctx, token), module, map)
        }
    }
}