package ru.DmN.pht.std.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent
import ru.DmN.pht.std.utils.text

class NodeNewArray(tkOperation: Token, val type: VirtualType, val size: Node) : Node(tkOperation) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        size.print(
            builder.indent(indent).append('[').append(text)
                .append('\n').indent(indent + 1).append(type.name)
                .append('\n'), indent + 1)
            .append('\n').indent(indent).append(']')
}