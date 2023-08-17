package ru.DmN.pht.base

import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.unparsers.NUUse
import ru.DmN.pht.base.unparsers.NodeUnparser
import ru.DmN.pht.std.utils.Module
import java.util.ArrayList

class Unparser {
    val modules: MutableList<Module> = ArrayList()
    val unparsers: MutableMap<String, NodeUnparser<*>> = DEFAULT_PARSERS.toMutableMap()
    val out = StringBuilder()

    fun unparse(node: Node) = (unparsers[node.tkOperation.text!!] as NodeUnparser<Node>).unparse(this, node)

    companion object {
        private val DEFAULT_PARSERS: Map<String, NodeUnparser<*>>

        init {
            DEFAULT_PARSERS = HashMap()
            // use
            DEFAULT_PARSERS["use"] = NUUse
            // Блок
            DEFAULT_PARSERS["progn"] = NUUse
        }
    }
}