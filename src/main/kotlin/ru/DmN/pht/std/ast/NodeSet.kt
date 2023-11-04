package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.std.utils.text

open class NodeSet(token: Token, val name: String, var value: Node?) : Node(token) {
    override fun copy(): NodeSet =
        NodeSet(token, name, value?.copy())

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(text).append('\n').indent(indent + 1).append(name).append('\n')
        if (value != null)
            value!!.print(builder, indent + 1).append('\n')
        indent(indent).append(']')
    }
}