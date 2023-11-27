package ru.DmN.pht.std.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import java.util.UUID

class NodeDefMacro(tkOperation: Token, nodes: MutableList<Node>, val uuid: UUID) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeDefMacro =
        NodeDefMacro(token, copyNodes(), uuid)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(token.text).append(" (").append(uuid).append(')'), indent).append(']')
}