package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.std.utils.text

class NodeNewArray(tkOperation: Token, val type: VirtualType, val size: Node) : Node(tkOperation) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        size.print(
            builder.indent(indent).append('[').append(text)
                .append('\n').indent(indent + 1).append(type.name)
                .append('\n'), indent + 1)
            .append('\n').indent(indent).append(']')
}