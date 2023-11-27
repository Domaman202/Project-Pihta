package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.text

class NodeNs(tkOperation: Token, nodes: MutableList<Node>, val namespace: String) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeNs =
        NodeNs(token, copyNodes(), namespace)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(text).append(' ').append(namespace), indent).append(']')
}