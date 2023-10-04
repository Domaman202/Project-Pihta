package ru.DmN.pht.std.collections.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node

class NodeAGet(tkOperation: Token, val arr: Node, val index: Node) : Node(tkOperation)