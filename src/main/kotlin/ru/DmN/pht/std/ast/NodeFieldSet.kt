package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.utils.indent

class NodeFieldSet(token: Token, nodes: MutableList<Node>, val instance: Node, name: String, val static: Boolean, val native: Boolean = false) : NodeSet(token, nodes, name) {
    override fun copy(): NodeFieldSet =
        NodeFieldSet(token, copyNodes(), instance, name, static)

    // todo:
//    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
//        value!!.print(instance.print(builder.indent(indent).append('[').append(token.text).append(if (static) " (static)" else " (nostatic)").append('\n').indent(indent + 1).append("name = ").append(name).append('\n'), indent + 1).append('\n'), indent + 1).append('\n').indent(indent).append(']')
}