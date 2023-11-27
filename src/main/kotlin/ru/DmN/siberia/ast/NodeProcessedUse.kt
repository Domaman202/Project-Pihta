package ru.DmN.siberia.ast

import ru.DmN.pht.base.lexer.Token

class NodeProcessedUse(
    tkOperation: Token,
    names: List<String>,
    nodes: MutableList<Node>,
    val processed: MutableList<Node>,
    val exports: MutableList<NodeNodesList>
) : NodeUse(tkOperation, names, nodes)