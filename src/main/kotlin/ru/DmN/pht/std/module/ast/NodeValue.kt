package ru.DmN.pht.std.module.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node

class NodeValue(tkOperation: Token, override val value: String) : Node(tkOperation), IValueNode