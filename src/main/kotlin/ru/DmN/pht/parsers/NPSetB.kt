package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeFMGet
import ru.DmN.pht.ast.NodeFieldSet
import ru.DmN.pht.ast.NodeGetOrName
import ru.DmN.pht.ast.NodeSet
import ru.DmN.pht.utils.node.NodeParsedTypes.*
import ru.DmN.pht.utils.node.NodeTypes.GET_OR_NAME
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.node.INodeInfo

object NPSetB : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val name = parser.nextToken()!!
        return NPProgn.parse(parser, ctx) {
            parse(
                INodeInfo.of(SET_B),
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

    private fun parse(info: INodeInfo, name: String, static: Boolean, nodes: MutableList<Node>): Node {
        val parts = name.split("/")
        return if (parts.size == 1)
            NodeSet(
                info,
                nodes,
                parts.last()
            )
        else NodeFieldSet(
            info.withType(FSET_B),
            nodes,
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
            else NodeGetOrName(info.withType(GET_OR_NAME), name, false)
        else NodeFMGet(
            info.withType(FGET_B),
            mutableListOf(),
            parse(info, parts, i + 1, static),
            name,
            static
        )
    }
}