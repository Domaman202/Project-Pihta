package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent

class NodeAs(tkOperation: Token, nodes: MutableList<Node>, generics: List<VirtualType>, val type: VirtualType) : NodeGensNodesList(tkOperation, nodes, generics) {
    override fun copy(): NodeAs =
        NodeAs(token, copyNodes(), generics, type)

    override fun withGenerics(generics: List<VirtualType>): NodeAs =
        NodeAs(token, copyNodes(), generics, type)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(token.text).append('\n').indent(indent + 1).append("type = ").append(type.name), indent).append(']')
}