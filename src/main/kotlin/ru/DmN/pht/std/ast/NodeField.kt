package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.IStaticVariantNode
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.indent

class NodeField(tkOperation: Token, val fields: List<Pair<String, String>>, override var static: Boolean) : Node(tkOperation), IStaticVariantNode {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(tkOperation.text).append(' ').append(if (static) "static" else "nostatic")
        if (fields.isNotEmpty())
            builder.append('\n')
        fields.forEach { builder.indent(indent + 1).append('[').append(it.second).append(' ').append(it.first).append("]\n") }
        if (fields.isNotEmpty())
            builder.indent(indent)
        return builder.append(']')
    }
}