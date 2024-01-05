package ru.DmN.pht.std.ast

import ru.DmN.pht.ast.IOpenlyNode
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo

class NodeModifierNodesList(info: INodeInfo, nodes: MutableList<Node>) : NodeNodesList(info, nodes), IAbstractlyNode, IFinallyNode, IOpenlyNode, IStaticallyNode {
    override var abstract: Boolean = false
        set(value) { field = value; nodes.filter { it is IAbstractlyNode }.forEach { (it as IAbstractlyNode).abstract = value } }
    override var final: Boolean = false
        set(value) { field = value; nodes.filter { it is IFinallyNode }.forEach { (it as IFinallyNode).final = value } }
    override var open: Boolean = false
        set(value) { field = value; nodes.filter { it is IOpenlyNode }.forEach { (it as IOpenlyNode).open = value } }
    override var static: Boolean = false
        set(value) { field = value; nodes.filter { it is IStaticallyNode }.forEach { (it as IStaticallyNode).static = value } }

    override fun copy(): NodeModifierNodesList =
        NodeModifierNodesList(info, copyNodes())
}