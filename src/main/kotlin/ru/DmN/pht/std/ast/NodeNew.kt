package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.text

class NodeNew(tkOperation: Token, nodes: MutableList<Node>, val ctor: VirtualMethod) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeNew =
        NodeNew(token, copyNodes(), ctor)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(text).append('\n').indent(indent + 1).append("type = ").append(ctor.declaringClass!!.name), indent).append(']')
}