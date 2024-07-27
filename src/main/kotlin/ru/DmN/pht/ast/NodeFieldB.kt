package ru.DmN.pht.ast

import ru.DmN.pht.utils.meta.MetadataKeys.*
import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.meta.IMetadataKey
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualField

class NodeFieldB(
    info: INodeInfo,
    val fields: List<VirtualField.VirtualFieldImpl>
) : BaseNode(info), IStaticallyNode, IOpenlyNode, IFinallyNode {
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

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type)
        if (fields.isNotEmpty()) {
            val modifiers: String? =
                if (final)
                    if (static)
                        "static final"
                    else "final"
                else if (static)
                    "static"
                else null
            fields.forEach { it ->
                append('\n').indent(indent + 1).append('[').append('\n')
                modifiers?.let { indent(indent + 2).append('(').append(it).append(")\n") }
                indent(indent + 2).append("(name = ").append(it.name).append(")\n")
                indent(indent + 2).append("(type = ").append(it.type).append(")\n")
                indent(indent + 1).append(']')
            }
            append('\n')
        }
        append(']')
    }
}