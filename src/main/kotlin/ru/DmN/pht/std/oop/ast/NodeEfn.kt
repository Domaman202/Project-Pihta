package ru.DmN.pht.std.oop.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType

class NodeEfn(tkOperation: Token, nodes: MutableList<Node>, val extend: VirtualType, val method: VirtualMethod) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeEfn =
        NodeEfn(tkOperation, copyNodes(), extend, method)
}