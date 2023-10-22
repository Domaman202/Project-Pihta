package ru.DmN.pht.std.ast

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.indent

class NodeNew(tkOperation: Token, nodes: MutableList<Node>, val ctor: VirtualMethod) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeNew =
        NodeNew(token, copyNodes(), ctor)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(token.text).append('\n').indent(indent + 1).append("type = ").append(ctor.declaringClass!!.name), indent).append(']')
}