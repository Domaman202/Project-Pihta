package ru.DmN.phtx.pcl.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.indent

open class NodeElement(token: Token, val offset: Int) : Node(token) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append("(${token.line}, $offset)")
        return printWLAF(builder, indent)
    }

    /**
     * Print Without Line And Offset
     */
    open fun printWLAF(builder: StringBuilder, indent: Int): StringBuilder =
        builder
}