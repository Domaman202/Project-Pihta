package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.std.utils.text

class NodeNs(tkOperation: Token, nodes: MutableList<Node>, val namespace: String) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeNs =
        NodeNs(token, copyNodes(), namespace)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(text).append(' ').append(namespace), indent).append(']')
}