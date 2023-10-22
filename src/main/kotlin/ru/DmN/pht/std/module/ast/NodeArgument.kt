package ru.DmN.pht.std.module.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node

class NodeArgument(tkOperation: Token, override val value: Any?) : Node(tkOperation), IValueNode