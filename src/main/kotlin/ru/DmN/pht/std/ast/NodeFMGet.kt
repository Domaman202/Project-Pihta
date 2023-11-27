package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.indent

class NodeFMGet(token: Token, val instance: Node, name: String, static: Boolean, val native: Boolean = false) : NodeGetOrName(token, name, static), INodesList {
    override val nodes: MutableList<Node> = mutableListOf()

    override fun isLiteral(): Boolean =
        false

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        instance.print(builder.indent(indent).append('[').append(token.text).append(if (static) " (static)" else " (nostatic)").append('\n').indent(indent + 1).append("name = ").append(name).append('\n'), indent + 1).append('\n').indent(indent).append(']')
}