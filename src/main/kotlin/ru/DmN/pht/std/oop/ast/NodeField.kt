package ru.DmN.pht.std.oop.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.indent

class NodeField(tkOperation: Token, val fields: List<Pair<String, String>>) : Node(tkOperation), IStaticallyNode, IFinally {
    override var static: Boolean = false
    override var final: Boolean = false

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.apply {
            indent(indent).append('[').append(tkOperation.text).append(' ')
                .append(if (static) "static" else "nostatic").append(' ')
                .append(if (final) "final" else "nofinal")
            fields.forEach {
                append('\n').indent(indent + 1).append("[\n")
                    .indent(indent + 2).append("name = ").append(it.first)
                    .append('\n').indent(indent + 2).append("type = ").append(it.second)
                append('\n').indent(indent + 1).append("]\n").indent(indent)
            }
            append(']')
        }
}