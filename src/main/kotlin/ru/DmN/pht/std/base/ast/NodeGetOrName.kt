package ru.DmN.pht.std.base.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.indent

open class NodeGetOrName(tkOperation: Token, val name: String, val static: Boolean) : Node(tkOperation) {
    override fun getValueAsString(): String = name

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        return builder.indent(indent).append('[').append(tkOperation.text).append("] ").append(if (static) "static" else "nostatic").append(' ').append(name)
    }
}