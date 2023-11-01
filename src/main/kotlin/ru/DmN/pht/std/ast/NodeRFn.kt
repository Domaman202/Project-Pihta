package ru.DmN.pht.std.ast

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType

class NodeRFn(
    token: Token,
    nodes: MutableList<Node>,
    type: VirtualType?,
    args: List<String>,
    val name: String,
    val refs: List<Variable>
) : NodeFn(token, nodes, type, args)