package ru.DmN.pht.ast

import ru.DmN.pht.processor.utils.Variable
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualField.VirtualFieldImpl
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent

class NodeDef(info: INodeInfo, val variables: List<VariableOrField>, val isVariable: Boolean) : Node(info), IStaticallyNode, IFinallyNode, IOpenlyNode {
    override var static: Boolean = false
        set(value) { field = value; variables.asSequence().map { it.field?.modifiers }.filterNotNull().forEach { it.isStatic = value } }
    override var final: Boolean = false
        set(value) { field = value; variables.asSequence().map { it.field?.modifiers }.filterNotNull().forEach { it.isFinal = value } }
    override var open: Boolean = false

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type)
        // todo: modifiers
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