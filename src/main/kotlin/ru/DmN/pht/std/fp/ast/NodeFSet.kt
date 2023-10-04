package ru.DmN.pht.std.fp.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList

class NodeFSet(tkOperation: Token, nodes: MutableList<Node>, val name: String, val type: Type) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeFSet =
        NodeFSet(tkOperation, copyNodes(), name, type)

    enum class Type {
        UNKNOWN,
        STATIC,
        INSTANCE
    }
}