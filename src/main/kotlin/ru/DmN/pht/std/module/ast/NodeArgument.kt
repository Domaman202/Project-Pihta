package ru.DmN.pht.std.module.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node

class NodeArgument(tkOperation: Token, override val value: Any?) : Node(tkOperation), IValueNode