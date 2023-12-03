package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.VirtualType.VirtualTypeImpl
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.text

class NodeType(tkOperation: Token, nodes: MutableList<Node>, val type: VirtualTypeImpl) : NodeNodesList(tkOperation, nodes), IAbstractlyNode, IFinallyNode {
    override var abstract: Boolean
        get() = type.isAbstract
        set(value) { type.isAbstract = value }
    override var final: Boolean
        get() = type.isFinal
        set(value) { type.isFinal = value }

    override fun copy(): NodeType =
        NodeType(token, copyNodes(), type)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(text).append(' ').append(type.name).append(" (")
        type.parents.forEachIndexed { i, it ->
            builder.append(it.name)
            if (i + 1 < type.parents.size) {
                builder.append(' ')
            }
        }
        return printNodes(builder.append(')'), indent).append(']')
    }
}