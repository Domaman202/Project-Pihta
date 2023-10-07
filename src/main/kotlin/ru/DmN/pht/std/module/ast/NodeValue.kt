package ru.DmN.pht.std.module.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node

class NodeValue(tkOperation: Token, override val value: String) : Node(tkOperation), IValueNode