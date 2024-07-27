package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo
import java.util.concurrent.atomic.AtomicReference

class NodeLazySymbol(
    info: INodeInfo,
    nodes: MutableList<Node>,
    val symbol: AtomicReference<String?>
) : NodeNodesList(info, nodes) {
    override fun copy(): NodeLazySymbol =
        NodeLazySymbol(info, copyNodes(), symbol)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
        indent(indent + 1).append("(symbol = ").append(symbol.get()).append(')')
        printNodes(builder, indent)
        append(']')
    }
}