package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.VirtualField.VirtualFieldImpl
import ru.DmN.siberia.utils.indent

class NodeFieldB(tkOperation: Token, val fields: List<VirtualFieldImpl>) : Node(tkOperation), IStaticallyNode, IFinallyNode {
    override var static: Boolean = false
        set(value) { field = value; fields.forEach { it.isStatic = value } }
    override var final: Boolean = false

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(token.text).append(' ')
            .append(if (static) "(static)" else "(nostatic)").append(' ')
            .append(if (final) "(final)" else "(nofinal)")
        if (fields.isNotEmpty()) {
            fields.forEach {
                append('\n').indent(indent + 1).append("[\n")
                    .indent(indent + 2).append("name = ").append(it.name)
                    .append('\n').indent(indent + 2).append("type = ").append(it.type.name)
                append('\n').indent(indent + 1).append(']')
            }
            append('\n').indent(indent)
        }
        append(']')
    }
}