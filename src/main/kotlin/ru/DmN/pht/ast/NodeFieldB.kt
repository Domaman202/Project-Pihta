package ru.DmN.pht.ast

import ru.DmN.pht.utils.meta.MetadataKeys.*
import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.meta.IMetadataKey
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualField

class NodeFieldB(info: INodeInfo, val fields: List<VirtualField.VirtualFieldImpl>) : BaseNode(info), IStaticallyNode, IOpenlyNode, IFinallyNode {
    override var static: Boolean = false
        set(value) { field = value; fields.stream().map { it.modifiers }.forEach { it.isStatic = value } }
    override var final: Boolean = false
        set(value) { field = value; fields.stream().map { it.modifiers }.forEach { it.isFinal = value } }
    override var open: Boolean = false

    override fun setMetadata(key: IMetadataKey, value: Any?) {
        when (key) {
            FINAL  -> final  = value as Boolean
            OPEN   -> open   = value as Boolean
            STATIC -> static = value as Boolean
        }
    }

    override fun getMetadata(key: IMetadataKey): Any? =
        when (key) {
            FINAL  -> final
            OPEN   -> open
            STATIC -> static
            else   -> null
        }
}