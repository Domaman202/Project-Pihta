package ru.DmN.pht.std.imports.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node

class NodeValue(tkOperation: Token, override val value: String) : Node(tkOperation), IValueNode