package ru.DmN.pht.std.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.indent

class NodeEField(tkOperation: Token, val fields: List<Pair<String, List<Node>>>) : Node(tkOperation) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(token.text)
        if (fields.isNotEmpty()) {
            append('\n')
            fields.forEach { it ->
                indent(indent + 1).append('(').append(it.first).append(')').append('\n')
                it.second.forEach { it.print(builder, indent + 2).append('\n') }
            }
            indent(indent)
        }
        append(']')
    }
}