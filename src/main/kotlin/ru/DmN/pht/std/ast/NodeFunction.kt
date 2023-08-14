package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.IStaticVariantNode
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.std.utils.Arguments
import ru.DmN.pht.base.utils.indent

open class NodeFunction(
    tkOperation: Token,
    var name: String,
    val rettype: String,
    val args: Arguments,
    static: Boolean,
    val abstract: Boolean,
    val override: Boolean,
    nodes: MutableList<Node>
) : NodeNodesList(tkOperation, nodes), IStaticVariantNode {
    final override var static: Boolean = static
        set(value) {
            field = value
            if (value && tkOperation.text == "ctor") {
                name = "<clinit>"
            }
        }

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(tkOperation.text).append(if (static) " static\n" else " nostatic\n")
            .indent(indent + 1).append("name = $name\n")
            .indent(indent + 1).append("rettype = $rettype\n")
        args.print(builder, indent + 1).append('\n').indent(indent + 1).append('\n')
        nodes.forEach { it.print(builder, indent + 1).append('\n') }
        return builder.indent(indent).append(']')
    }
}