package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.indent

class NodeDefn(info: INodeInfo, nodes: MutableList<Node>, val method: VirtualMethod) : NodeNodesList(info, nodes), IAbstractlyNode, IStaticallyNode, IVarargNode {
    override var abstract: Boolean
        set(value) { method.modifiers.abstract = value }
        get() = method.modifiers.abstract
    override var static: Boolean
        set(value) { method.modifiers.static = value }
        get() = method.modifiers.static
    override var varargs: Boolean
        set(value) { method.modifiers.varargs = value }
        get() = method.modifiers.varargs

    override fun copy(): NodeDefn =
        NodeDefn(info, copyNodes(), method).apply { static = this@NodeDefn.static }

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(type = ")
        if (method.modifiers.varargs)
            append("varargs ")
        append(
            if (method.modifiers.ctor)
                "constructor"
            else if (method.modifiers.abstract)
                "abstract method"
            else if (method.modifiers.extension)
                "extension method"
            else "method"
        ).append(")\n")
        if (short)
            indent(indent + 1).append("(desc = ").append(method.name).append(method.desc).append(')')
        else indent(indent + 1).append("(name = ").append(method.name).append(")\n")
            .indent(indent + 1).append("(desc = ").append(method.desc).append(")\n")
            .indent(indent + 1).append("(sign = ").append(method.signature).append(')')
        if (nodes.isEmpty())
            append('\n').indent(indent)
        else printNodes(this, indent, short)
        append(']')
    }
}