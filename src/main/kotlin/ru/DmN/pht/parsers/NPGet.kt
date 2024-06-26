package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeFMGet
import ru.DmN.pht.parser.utils.isNaming
import ru.DmN.pht.parser.utils.isOperation
import ru.DmN.pht.utils.node.NodeParsedTypes.FGET_B
import ru.DmN.pht.utils.node.NodeParsedTypes.GET
import ru.DmN.pht.utils.node.nodeBGet
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.node.INodeInfo

object NPGet : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val nameToken = parser.nextToken()!!
        return NPProgn.parse(parser, ctx) { parse(nameToken, it, parser, ctx, token) }
    }

    fun parse(nameToken: Token, nodes: MutableList<Node>, parser: Parser, ctx: ParsingContext, token: Token): Node {
        val info = INodeInfo.of(GET, ctx, token)
        return when (nameToken.type) {
            Token.DefaultType.CLASS -> parse(
                info,
                nameToken.text!!,
                nodes,
                static = true,
                klass = true
            )
            Token.DefaultType.STRING,
            Token.DefaultType.OPERATION -> parse(
                info,
                nameToken.text!!,
                nodes,
                static = false,
                klass = false
            )
            Token.DefaultType.OPEN_BRACKET -> {
                parser.pushToken(nameToken)
                NodeFMGet(
                    info,
                    nodes,
                    parser.parseNode(ctx)!!,
                    parser.nextToken()!!.let { if (it.isOperation() || it.isNaming()) it else throw RuntimeException() }.text!!,
                    false
                )
            }

            else -> throw RuntimeException()
        }
    }

    fun parse(info: INodeInfo, name: String, nodes: MutableList<Node>, static: Boolean, klass: Boolean): Node {
        val parts = name.split('/')
        return parse(info, parts, parts.size, nodes, static, klass)
    }

    private fun parse(info: INodeInfo, parts: List<String>, i: Int, nodes: MutableList<Node>, static: Boolean, clazz: Boolean): Node {
        val j = i - 1
        return if (j == 0) {
            if (clazz)
                nodeValueClass(info, parts[0])
            else {
                val first = parts[0]
                val k = first.indexOf('@')
                if (k > -1)
                    nodeBGet(info, first.substring(0, k), first.substring(k + 1))
                else NodeNodesList(info.withType(GET), nodes.apply { add(0, nodeValue(info, first)) })
            }
        } else {
            val isStatic = static && j == 1
            NodeFMGet(
                info.withType(FGET_B),
                nodes,
                parse(info, parts, j, nodes, static, clazz),
                parts[j],
                isStatic
            )
        }
    }
}