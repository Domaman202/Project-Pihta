package ru.DmN.pht.std.macro.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.indent
import java.util.UUID

class NodeDefMacro(tkOperation: Token, nodes: MutableList<Node>, val uuid: UUID) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeDefMacro =
        NodeDefMacro(tkOperation, copyNodes(), uuid)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(tkOperation.text).append(" (").append(uuid).append(')'), indent).append(']')
}