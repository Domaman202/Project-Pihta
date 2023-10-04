package ru.DmN.pht.std.fp.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.std.oop.ast.IAbstractlyNode
import ru.DmN.pht.std.oop.ast.IStaticallyNode

class NodeDefn(
    tkOperation: Token,
    nodes: MutableList<Node>,
    val method: VirtualMethod
) : NodeNodesList(tkOperation, nodes), IStaticallyNode, IAbstractlyNode {
    override var static: Boolean = false
    override var abstract: Boolean = false

    override fun copy(): NodeDefn =
        NodeDefn(tkOperation, copyNodes(), method).apply { static = this@NodeDefn.static }

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(tkOperation.text).append(' ')
        if (method.modifiers.ctor)
            builder.append('(').append(method.argsDesc).append(')')
        else builder.append(method.name).append(method.desc)
        return printNodes(builder, indent).append(']')
    }
}