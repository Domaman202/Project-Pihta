package ru.DmN.pht.std.fp.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.std.base.processor.utils.Variable

class NodeDef(tkOperation: Token, val variables: List<Variable>) : Node(tkOperation) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.apply {
            indent(indent).append('[').append(tkOperation.text)
            variables.forEach {
                append('\n').indent(indent + 1).append("[\n")
                    .indent(indent + 2).append("name = ").append(it.name)
                    .append('\n').indent(indent + 2).append("type = ").append(it.type)
                it.value?.print(builder.append('\n'), indent + 2)
                append('\n').indent(indent + 1).append("]\n").indent(indent)
            }
            append(']')
        }
}