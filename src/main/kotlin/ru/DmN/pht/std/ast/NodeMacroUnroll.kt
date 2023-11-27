package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.text
import java.util.*

class NodeMacroUnroll(tkOperation: Token, nodes: MutableList<Node>, val uuids: List<UUID>) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeMacroUnroll =
        NodeMacroUnroll(token, copyNodes(), uuids)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(text).append(" (").append(uuids).append(')'), indent).append(']')
}