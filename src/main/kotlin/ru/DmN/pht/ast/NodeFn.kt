package ru.DmN.pht.ast

import ru.DmN.pht.utils.NVC
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType

class NodeFn(info: INodeInfo, val source: Source, var processed: MutableList<Node>? = null) : Node(info), INodesList {
    override val nodes: MutableList<Node>
        get() = processed ?: source.nodes

    data class Source (
        val nodes: MutableList<Node>,
        var type: VirtualType?,
        val args: List<String>,
        val name: String,
        val refs: List<NVC>
    )
}