package ru.DmN.pht.ast

import ru.DmN.pht.processor.utils.Variable
import ru.DmN.pht.utils.meta.MetadataKeys.*
import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.meta.IMetadataKey
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualField.VirtualFieldImpl
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeDef(info: INodeInfo, val variables: List<VariableOrField>, val isVariable: Boolean) : BaseNode(info), IStaticallyNode, IFinallyNode, IOpenlyNode {
    override var static: Boolean = false
        set(value) {
            field = value
            variables.forEach { it ->
                it.field?.let {
                    it.modifiers.isStatic = value
                }
            }
        }

    override var final: Boolean = false
        set(value) {
            field = value
            variables.forEach { it ->
                it.field?.let {
                    it.modifiers.isFinal = value
                }
            }
        }

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

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append('(')
        if (static)
            append("static ")
        else if (open)
            append("open ")
        if (final)
            append("final ")
        append(if (isVariable) "var)" else "field)")
        if (variables.isNotEmpty()) {
            variables.forEach {
                append('\n').indent(indent + 1).append("[\n")
                    .indent(indent + 2).append("(name = ").append(it.name).append(")\n")
                    .indent(indent + 2).append("(type = ").append(it.type.name).append(')')
                it.value?.print(append('\n'), indent + 2, short)
                append('\n').indent(indent + 1).append(']')
            }
            append('\n').indent(indent)
        }
        append(']')
    }

    class VariableOrField(val variable: Variable?, val field: VirtualFieldImpl?) {
        val name: String =
            variable?.name ?: field!!.name
        val type: VirtualType =
            variable?.type ?: field!!.type
        val value: Node? =
            variable?.value

        companion object {
            fun of(variable: Variable): VariableOrField =
                VariableOrField(variable, null)
            fun of(field: VirtualFieldImpl): VariableOrField =
                VariableOrField(null, field)
        }
    }
}