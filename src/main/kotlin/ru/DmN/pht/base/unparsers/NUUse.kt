package ru.DmN.pht.base.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.NodeUse
import ru.DmN.pht.std.utils.Module

object NUUse : NodeUnparser<NodeUse>() {
    override fun unparse(unparser: Unparser, node: NodeUse) {
        unparser.out.let {
            it.append('(').append(node.tkOperation.text)
            node.names.forEach { name ->
                it.append(' ').append(name)
                Module.MODULES[name]!!.inject(unparser)
            }
            it.append(')')
        }
    }
}