package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.ast.*
import ru.DmN.pht.std.node.NodeParsedTypes
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.utils.nodeValueClass
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.node.INodeType
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn

object NPSetB : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val name = parser.nextToken()!!
        return NPProgn.parse(parser, ctx) {
            parse(
                INodeInfo.of(NodeParsedTypes.SET_B),
                name.text!!,
                when (name.type) {
                    Token.DefaultType.CLASS -> true
                    Token.DefaultType.OPERATION -> false
                    else -> throw RuntimeException()
                },
                it
            )
        }
    }

    private fun parse(info: INodeInfo, name: String, static: Boolean, values: MutableList<Node>): Node {
        val parts = name.split("/")
        return if (parts.size == 1)
            NodeSet(
                info,
                parts.last(),
                values.first()
            )
        else NodeFieldSet(
            info.withType(NodeParsedTypes.FSET_B),
            values,
            parse(info, parts, 1, static),
            parts.last(),
            static
        )
    }

    private fun parse(info: INodeInfo, parts: List<String>, i: Int, static: Boolean): Node {
        val name = parts[parts.size - i - 1]
        return if (i + 1 == parts.size)
            if (static)
                nodeValueClass(info, name)
            else NodeGetOrName(info.withType(NodeTypes.GET_OR_NAME), name, false)
        else NodeFMGet(
            info.withType(NodeParsedTypes.FGET_B),
            parse(info, parts, i + 1, static),
            name,
            static
        )
    }
}