package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.std.ast.NodeVar
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUDef : NodeUnparser<NodeVar>() {
    override fun unparse(unparser: Unparser, node: NodeVar) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(" [")
            node.variables.forEach { it ->
                append('[').append(it.first)
                it.second?.let {
                    append(' ')
                    unparser.unparse(it)
                }
                append(']')
            }
            append("])")
        }
    }
}