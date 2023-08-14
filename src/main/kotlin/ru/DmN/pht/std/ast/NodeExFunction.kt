package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.std.utils.Arguments

class NodeExFunction(
    tkOperation: Token,
    val clazz: String,
    name: String,
    rettype: String,
    args: Arguments,
    nodes: MutableList<Node>
) : NodeFunction(tkOperation, name, rettype, args, true, false, false, nodes)