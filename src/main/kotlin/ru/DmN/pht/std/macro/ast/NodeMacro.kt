package ru.DmN.pht.std.macro.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.indent

class NodeMacro(tkOperation: Token, nodes: MutableList<Node>, val name: String) : NodeNodesList(tkOperation, nodes) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(tkOperation.text).append(' ').append(name).append(" [")
        return printNodes(builder.append(']'), indent).append(']')
    }
}