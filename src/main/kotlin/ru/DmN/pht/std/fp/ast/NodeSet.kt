package ru.DmN.pht.std.fp.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node

open class NodeSet(tkOperation: Token, val name: String, var value: Node?) : Node(tkOperation) {
    override fun copy(): NodeSet =
        NodeSet(tkOperation, name, value?.copy())
}