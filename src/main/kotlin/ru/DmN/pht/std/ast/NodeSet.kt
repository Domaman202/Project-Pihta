package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.indent

open class NodeSet(tkOperation: Token, val name: String, val value: Node?) : Node(tkOperation) {
    override val nodes: List<Node>
        get() = if (value == null) emptyList() else listOf(value)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append("[${tkOperation.text}\n").indent(indent + 1).append(name).append('\n')
        nodes.forEach { it.print(builder, indent + 1).append('\n') }
        return builder.indent(indent).append(']')
    }
}