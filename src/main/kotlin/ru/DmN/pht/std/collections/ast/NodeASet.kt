package ru.DmN.pht.std.collections.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node

class NodeASet(tkOperation: Token, val arr: Node, val index: Node, val value: Node) : Node(tkOperation)