package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.indent

class NodeMCall(tkOperation: Token, nodes: MutableList<Node>, val instance: Node, val method: VirtualMethod, val type: Type) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeMCall =
        NodeMCall(token, copyNodes(), instance, method, type)

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
        // for processor & compiler
        SUPER,
        // for processor
        THIS,
        INSTANCE
    }
}