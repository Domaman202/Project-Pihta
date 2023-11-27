package ru.DmN.pht.std.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.VirtualField.VirtualFieldImpl
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent
import ru.DmN.pht.std.processor.utils.Variable

class NodeDef(tkOperation: Token, val variables: List<VariableOrField>) : Node(tkOperation), IStaticallyNode, IFinallyNode {
    override var static: Boolean = false
        set(value) { field = value; variables.forEach { it.field?.isStatic = true } }
    override var final: Boolean = false

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(token.text)
        if (variables.isNotEmpty()) {
            variables.forEach {
                append('\n').indent(indent + 1).append("[\n")
                    .indent(indent + 2).append("name = ").append(it.name)
                    .append('\n').indent(indent + 2).append("type = ").append(it.type.name)
                it.value?.print(append('\n'), indent + 2)
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