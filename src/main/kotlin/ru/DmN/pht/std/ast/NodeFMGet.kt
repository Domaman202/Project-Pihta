package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.indent

class NodeFMGet(tkOperation: Token, val instance: Node, name: String, static: Boolean) : NodeGet(tkOperation, name, static) {
    override val nodes: List<Node>
        get() = listOf(instance)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(tkOperation.text).append("] ").append(if (static) "static" else "nostatic").append(' ').append(name)
        if (nodes.isNotEmpty())
            builder.append('\n')
        nodes.forEach { it.print(builder, indent + 1).append('\n') }
        if (nodes.isNotEmpty())
            builder.indent(indent)
        return builder.append(']')
    }
}