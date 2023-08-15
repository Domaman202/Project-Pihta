package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.indent

class NodeMethodCall(tkOperation: Token, val name: String, nodes: MutableList<Node>, override var static: Boolean) : NodeNodesList(tkOperation, nodes), IStaticVariantNode {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(tkOperation.text).append(']').append(if (static) " static " else " nostatic ").append(name).append('\n')
        nodes.forEach { it.print(builder, indent + 1).append('\n') }
        return builder.indent(indent).append(']')
    }
}