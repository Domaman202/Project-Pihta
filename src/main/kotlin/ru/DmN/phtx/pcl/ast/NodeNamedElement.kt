package ru.DmN.phtx.pcl.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.indent

class NodeNamedElement(token: Token, offset: Int, val name: String, val node: NodeElement) : NodeElement(token, offset) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('(').append(token.line).append(", ").append(offset).append(") ")
        return printWLAF(builder, indent)
    }

    override fun printWLAF(builder: StringBuilder, indent: Int): StringBuilder {
        builder.append(name).append('\n').indent(indent)
        return node.printWLAF(builder, indent)
    }
}