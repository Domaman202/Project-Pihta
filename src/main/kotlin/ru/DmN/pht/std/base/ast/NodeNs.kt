package ru.DmN.pht.std.base.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.indent

class NodeNs(tkOperation: Token, nodes: MutableList<Node>, val namespace: String) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeNs =
        NodeNs(tkOperation, copyNodes(), namespace)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(tkOperation.text).append(' ').append(namespace), indent).append(']')
}