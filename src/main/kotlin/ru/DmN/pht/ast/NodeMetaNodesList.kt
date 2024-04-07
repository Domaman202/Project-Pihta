package ru.DmN.pht.ast

import ru.DmN.siberia.ast.BaseMetaNode
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.mapMutable
import ru.DmN.siberia.utils.meta.IMetadataKey
import ru.DmN.siberia.utils.meta.MetadataContainer
import ru.DmN.siberia.utils.node.INodeInfo

open class NodeMetaNodesList(info: INodeInfo, metadata: Lazy<MetadataContainer>, override val nodes: MutableList<Node>) : BaseMetaNode(info, metadata), INodesList {
    constructor(info: INodeInfo) : this(info, lazy { MetadataContainer() }, mutableListOf())
    constructor(info: INodeInfo, nodes: MutableList<Node>) : this(info, lazy { MetadataContainer() }, nodes)

    override fun setMetadata(key: IMetadataKey, value: Any?) {
        super<BaseMetaNode>.setMetadata(key, value)
        nodes.forEach { it.setMetadata(key, value) }
    }

    override fun copy(): NodeMetaNodesList =
        NodeMetaNodesList(info, copyMetadata(), copyNodes())

    fun copyMetadata(): Lazy<MetadataContainer> =
        if (metadata.isInitialized())
            lazyOf(metadata.value.copy())
        else metadata

    fun copyNodes(): MutableList<Node> =
        nodes.mapMutable { it.copy() }

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type)
        printMetadata(this, indent)
        printNodes(this, indent, short)
        append(']')
    }

    fun printNodes(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder {
        if (nodes.isNotEmpty())
            builder.append('\n')
        nodes.forEach { it.print(builder, indent + 1, short).append('\n') }
        if (nodes.isNotEmpty())
            builder.indent(indent)
        return builder
    }
}