package ru.DmN.pht.ast

import ru.DmN.pht.utils.meta.MetadataKeys.*
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.meta.IMetadataKey
import ru.DmN.siberia.utils.meta.MetadataContainer
import ru.DmN.siberia.utils.node.INodeInfo

class NodeType(
    info: INodeInfo,
    metadata: Lazy<MetadataContainer>,
    nodes: MutableList<Node>,
    val type: PhtVirtualType.Impl
) : NodeMetaNodesList(info, metadata, nodes), IAbstractlyNode, IFileNode, IOpenlyNode, ITestableNode {
    constructor(info: INodeInfo, type: PhtVirtualType.Impl) : this(info, lazy { MetadataContainer() }, mutableListOf(), type)
    constructor(info: INodeInfo, nodes: MutableList<Node>, type: PhtVirtualType.Impl) : this(info, lazy { MetadataContainer() }, nodes, type)

    override var abstract: Boolean
        set(value) { type.isAbstract = value }
        get() = type.isAbstract

    override var file: Boolean
        set(value) { type.isFile = value }
        get() = type.isFile

    override var open: Boolean
        set(value) { type.isFinal = !value }
        get() = !type.isFinal

    override var test: Boolean = false

    override fun setMetadata(key: IMetadataKey, value: Any?) {
        when (key) {
            ABSTRACT -> abstract = value as Boolean
            FILE     -> file     = value as Boolean
            OPEN     -> open     = value as Boolean
            STATIC   -> Unit
            TEST     -> test     = value as Boolean
            else     -> super<NodeMetaNodesList>.setMetadata(key, value)
        }
    }

    override fun getMetadata(key: IMetadataKey): Any? =
        when (key) {
            ABSTRACT -> abstract
            FILE     -> file
            OPEN     -> open
            TEST     -> test
            else     -> super<NodeMetaNodesList>.getMetadata(key)
        }

    override fun copy(): NodeType =
        NodeType(info, copyMetadata(), copyNodes(), type)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
        indent(indent + 1).append("(type = ").append(
            if (type.isInterface)
                "interface"
            else if (type.isAbstract)
                "abstract class"
            else if (type.isFinal)
                "final class"
            else "open class"
        ).append(")\n")
        indent(indent + 1).append("(name = ").append(type.name).append(')')
        if (type.parents.isNotEmpty()) {
            append('\n').indent(indent + 1).append("(parents = [")
            type.parents.forEachIndexed { i, it ->
                append(it.name)
                if (i + 1 < type.parents.size) {
                    append(' ')
                }
            }
            append("])")
        }
        printMetadata(builder, indent)
        printNodes(this, indent)
        append(']')
    }
}