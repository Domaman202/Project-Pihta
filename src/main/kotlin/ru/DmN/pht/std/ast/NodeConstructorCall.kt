package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList

class NodeConstructorCall(tkOperation: Token, nodes: MutableList<Node>, val supercall: Boolean) : NodeNodesList(tkOperation, nodes)