package ru.DmN.pht.std.ast

import ru.DmN.pht.std.utils.NVC
import ru.DmN.pht.std.utils.findLambdaMethod
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType

class NodeFn(info: INodeInfo, nodes: MutableList<Node>, var type: VirtualType?, val args: List<String>, val name: String, val refs: List<NVC>) : NodeNodesList(info, nodes), IAdaptableNode {
    override fun isAdaptableTo(type: VirtualType): Boolean =
        if (this.type == null)
            findLambdaMethod(type).argsn.size == args.size
        else this.type!!.isAssignableFrom(type)

    override fun adaptTo(type: VirtualType) {
        this.type = type
    }
}