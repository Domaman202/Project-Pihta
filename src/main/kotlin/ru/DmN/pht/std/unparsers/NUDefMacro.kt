package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser
import ru.DmN.pht.std.ast.NodeDefMacro

object NUDefMacro : NodeUnparser<NodeDefMacro>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: NodeDefMacro) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append(node.name).append(" [")
            node.args.forEach { append(' ').append(it) }
            append(']')
            if (node.nodes.isNotEmpty())
                append(' ')
            node.nodes.forEach { unparser.unparse(ctx, it) }
            append(')')
        }
    }
}