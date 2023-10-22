package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.std.processor.utils.Variable

class NodeDef(tkOperation: Token, val variables: List<Variable>) : Node(tkOperation) {
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
}