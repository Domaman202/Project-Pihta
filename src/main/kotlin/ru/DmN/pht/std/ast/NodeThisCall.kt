package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.indent

class NodeThisCall(tkOperation: Token, val name: String, nodes: MutableList<Node>) : NodeNodesList(tkOperation, nodes) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(tkOperation.text).append("] ").append(name)
        if (nodes.isNotEmpty())
            builder.append('\n')
        nodes.forEach { it.print(builder, indent + 1).append('\n') }
        if (nodes.isNotEmpty())
            builder.indent(indent)
        return builder.append(']')
    }
}