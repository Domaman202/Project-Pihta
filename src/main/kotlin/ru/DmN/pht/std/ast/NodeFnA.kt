package ru.DmN.pht.std.ast

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.utils.findLambdaMethod

open class NodeFnA(
    token: Token,
    nodes: MutableList<Node>,
    var type: VirtualType?,
    val args: List<String>
) : NodeNodesList(token, nodes), IAdaptableNode {
    override fun isAdaptableTo(type: VirtualType): Boolean =
        if (this.type == null)
            findLambdaMethod(type).argsn.size == args.size
        else this.type!!.isAssignableFrom(type)

    override fun adaptTo(type: VirtualType) {
        this.type = type
    }
}