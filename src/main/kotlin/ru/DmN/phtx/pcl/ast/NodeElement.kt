package ru.DmN.phtx.pcl.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.indent

open class NodeElement(token: Token, val offset: Int, val name: String) : Node(token) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.indent(indent).append("(${token.line}, $offset) $name")
}