package ru.DmN.pht.std.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent

class NodeMCall(tkOperation: Token, nodes: MutableList<Node>, generics: List<VirtualType>, val instance: Node, val method: VirtualMethod, val type: Type) : NodeGensNodesList(tkOperation, nodes, generics) {
    override fun copy(): NodeMCall =
        NodeMCall(token, copyNodes(), generics, instance, method, type)

    override fun withGenerics(generics: List<VirtualType>): NodeMCall =
        NodeMCall(token, copyNodes(), generics, instance, method, type)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(token.text).append(" (").append(type).append(") ").append(method.name).append(method.desc).append('\n')
        instance.print(builder, indent + 1)
        if (nodes.isEmpty())
            builder.append('\n').indent(indent)
        return printNodes(builder, indent).append(']')
    }

    enum class Type {
        // for compiler
        UNKNOWN,
        EXTEND,
        STATIC,
        VIRTUAL,
        SUPER,
        DYNAMIC
    }
}