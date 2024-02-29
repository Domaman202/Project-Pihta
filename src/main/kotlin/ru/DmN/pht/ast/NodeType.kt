package ru.DmN.pht.ast

import ru.DmN.pht.utils.meta.MetadataKeys.ABSTRACT
import ru.DmN.pht.utils.meta.MetadataKeys.OPEN
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.meta.IMetadataKey
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType.VirtualTypeImpl

class NodeType(info: INodeInfo, nodes: MutableList<Node>, val type: VirtualTypeImpl) : NodeNodesList(info, nodes), IAbstractlyNode, IOpenlyNode {
    override var abstract: Boolean
        set(value) {
            type.isAbstract = value
            nodes.forEach {
                if (it is IAbstractlyNode) {
                    it.abstract = value
                }
            }
        }
        get() = type.isAbstract

    override var open: Boolean
        set(value) {
            type.isFinal = !value
            nodes.forEach {
                if (it is IOpenlyNode) {
                    it.open = value
                }
            }
        }
        get() = !type.isFinal

    override fun setMetadata(key: IMetadataKey, value: Any?) {
        when (key) {
            ABSTRACT -> abstract = value as Boolean
            OPEN     -> open     = value as Boolean
        }
    }

    override fun getMetadata(key: IMetadataKey): Any? =
        when (key) {
            ABSTRACT -> abstract
            OPEN     -> open
            else     -> null
        }

    override fun copy(): NodeType =
        NodeType(info, copyNodes(), type)

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(type = ").append(
                if (type.isInterface)
                    "interface"
                else if (type.isAbstract)
                    "abstract class"
                else if (type.isFinal)
                    "final class"
                else "open class"
            ).append(")\n")
            .indent(indent + 1).append("(name = ").append(type.name).append(')')
        if (!short && type.parents.isNotEmpty()) {
            append('\n').indent(indent + 1).append("(parents = [")
            type.parents.forEachIndexed { i, it ->
                append(it.name)
                if (i + 1 < type.parents.size) {
                    append(' ')
                }
            }
            append("])")
        }
        printNodes(this, indent, short).append(']')
    }
}