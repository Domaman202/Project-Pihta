package ru.DmN.pht.std.macro.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.indent
import java.util.UUID

class NodeMacroUnroll(tkOperation: Token, nodes: MutableList<Node>, val uuids: List<UUID>) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeMacroUnroll =
        NodeMacroUnroll(tkOperation, copyNodes(), uuids)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(tkOperation.text).append(" (").append(uuids).append(')'), indent).append(']')
}