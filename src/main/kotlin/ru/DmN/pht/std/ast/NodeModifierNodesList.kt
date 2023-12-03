package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token

class NodeModifierNodesList(token: Token, nodes: MutableList<Node>) : NodeNodesList(token, nodes), IAbstractlyNode, IFinallyNode, IStaticallyNode {
    override var abstract: Boolean = false
        set(value) { field = value; nodes.filter { it is IAbstractlyNode }.forEach { (it as IAbstractlyNode).abstract = value } }
    override var final: Boolean = false
        set(value) { field = value; nodes.filter { it is IFinallyNode }.forEach { (it as IFinallyNode).final = value } }
    override var static: Boolean = false
        set(value) { field = value; nodes.filter { it is IStaticallyNode }.forEach { (it as IStaticallyNode).static = value } }
}