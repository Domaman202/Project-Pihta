package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.node.NodeParsedTypes
import ru.DmN.pht.std.node.nodeValue
import ru.DmN.pht.std.node.nodeValueClass
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.isNaming
import ru.DmN.siberia.lexer.isOperation
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser

object NPGet : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val info = INodeInfo.of(NodeParsedTypes.GET, ctx, token)
        val nameToken = parser.nextToken()!!
        return when (nameToken.type) {
            Token.DefaultType.CLASS -> parse(
                info,
                nameToken.text!!,
                static = true,
                klass = true
            )
            Token.DefaultType.OPERATION -> parse(
                info,
                nameToken.text!!,
                static = false,
                klass = false
            )
            Token.DefaultType.OPEN_BRACKET -> {
                parser.tokens.push(nameToken)
                return NodeFMGet(
                    info,
                    parser.parseNode(ctx)!!,
                    parser.nextToken()!!
                        .let { if (it.isOperation() || it.isNaming()) it else throw RuntimeException() }.text!!,
                    false
                )
            }

            else -> throw RuntimeException()
        }
    }

    private fun parse(info: INodeInfo, name: String, static: Boolean, klass: Boolean): Node {
        val parts = name.split("/", "#") as MutableList<String>
        return parse(info, parts, parts.size, static, klass)
    }

    private fun parse(info: INodeInfo, parts: List<String>, i: Int, static: Boolean, clazz: Boolean): Node {
        val j = i - 1
        return if (j == 0) {
            if (clazz)
                nodeValueClass(info, parts[0])
            else NodeNodesList(info.withType(NodeParsedTypes.GET), mutableListOf(nodeValue(info, parts[0])))
        } else {
            val isStatic = static && j == 1
            NodeFMGet(
                info.withType(NodeParsedTypes.FGET_B),
                parse(info, parts, j, static, clazz),
                parts[j],
                isStatic
            )
        }
    }
}