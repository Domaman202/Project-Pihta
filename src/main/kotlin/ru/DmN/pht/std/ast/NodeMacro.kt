package ru.DmN.pht.std.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent

class NodeMacro(tkOperation: Token, nodes: MutableList<Node>, val name: String) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeMacro =
        NodeMacro(token, copyNodes(), name)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(token.text).append(' ').append(name).append(" [")
        return printNodes(builder.append(']'), indent).append(']')
    }
}