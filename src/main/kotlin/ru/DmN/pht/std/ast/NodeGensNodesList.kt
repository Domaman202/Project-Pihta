package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.VirtualType

open class NodeGensNodesList(token: Token, nodes: MutableList<Node>, override val generics: List<VirtualType>) : NodeNodesList(token, nodes), IGenericsNode<NodeGensNodesList> {
    override fun copy(): NodeGensNodesList =
        NodeGensNodesList(token, copyNodes(), generics)

    override fun withGenerics(generics: List<VirtualType>): NodeGensNodesList =
        NodeGensNodesList(token, nodes, generics)
}