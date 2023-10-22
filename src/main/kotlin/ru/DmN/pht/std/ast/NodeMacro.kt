package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.utils.indent

class NodeMacro(tkOperation: Token, nodes: MutableList<Node>, val name: String) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeMacro =
        NodeMacro(token, copyNodes(), name)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(token.text).append(' ').append(name).append(" [")
        return printNodes(builder.append(']'), indent).append(']')
    }
}