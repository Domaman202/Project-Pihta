package ru.DmN.pht.std.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.pht.std.utils.text

open class NodeSet(token: Token, nodes: MutableList<Node>, val name: String) : NodeNodesList(token, nodes) {
    override fun copy(): NodeSet =
        NodeSet(token, copyNodes(), name)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(text).append('\n').indent(indent + 1).append(name).append('\n')
//        if (value != null)
//            value!!.print(builder, indent + 1).append('\n') // todo:
        indent(indent).append(']')
    }
}