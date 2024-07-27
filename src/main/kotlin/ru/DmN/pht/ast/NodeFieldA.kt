package ru.DmN.pht.ast

import ru.DmN.pht.utils.meta.MetadataKeys.FINAL
import ru.DmN.pht.utils.meta.MetadataKeys.STATIC
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.meta.IMetadataKey
import ru.DmN.siberia.utils.node.INodeInfo

class NodeFieldA(
    info: INodeInfo,
    nodes: MutableList<Node>,
    override var static: Boolean = false,
    override var final: Boolean = false
) : NodeNodesList(info, nodes), IStaticallyNode, IFinallyNode {
    override fun setMetadata(key: IMetadataKey, value: Any?) {
        when (key) {
            FINAL  -> final  = value as Boolean
            STATIC -> static = value as Boolean
        }
    }

    override fun getMetadata(key: IMetadataKey): Any? =
        when (key) {
            FINAL  -> final
            STATIC -> static
            else   -> null
        }

    override fun copy(): NodeNodesList =
        NodeFieldA(info, copyNodes(), static, final)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type)
        if (static || final) {
            append('\n')
            indent(indent + 1).append('(')
            append(if (static && final) "static final" else if (static) "static" else "final")
            append(')')
        }
        printNodes(builder, indent)
        append(']')
    }
}