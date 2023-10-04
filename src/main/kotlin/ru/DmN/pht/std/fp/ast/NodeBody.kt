package ru.DmN.pht.std.fp.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.std.fp.processor.ctx.BodyContext

class NodeBody(tkOperation: Token, nodes: MutableList<Node>) : NodeNodesList(tkOperation, nodes) {
    var ctx: BodyContext? = null
}