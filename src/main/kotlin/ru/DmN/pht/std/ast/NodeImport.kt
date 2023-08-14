package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.indent

class NodeImport(tkOperation: Token, val import: String) : Node(tkOperation) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.indent(indent).append('[').append(tkOperation.text).append("] ").append(import)
}