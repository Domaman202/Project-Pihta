package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.indent

class NodeVar(tkOperation: Token, val variables: List<Pair<String, Node?>>) : Node(tkOperation) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(tkOperation.text)
        if (variables.isNotEmpty())
            builder.append('\n')
        variables.forEach { it ->
            builder.indent(indent + 1).append("[ ").append(it.first)
            it.second?.let {
                builder.append('\n')
                it.print(builder, indent + 2).append('\n').indent(indent + 1).append(']').append('\n')
            }
        }
        if (variables.isNotEmpty())
            builder.indent(indent)
        return builder.append(']')
    }
}