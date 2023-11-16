package ru.DmN.pht.std.ast

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.utils.VirtualType.VirtualTypeImpl
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.std.utils.text

class NodeType(tkOperation: Token, nodes: MutableList<Node>, val type: VirtualTypeImpl) : NodeNodesList(tkOperation, nodes),
    IAbstractlyNode, IFinallyNode, IOpenlyNode {
    override var abstract: Boolean
        get() = type.isAbstract
        set(value) { type.isAbstract = value }
    override var final: Boolean
        get() = type.isFinal
        set(value) { type.isFinal = value }
    override var open: Boolean
        get() = !type.isFinal
        set(value) { type.isFinal = !value }

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