package ru.DmN.pht.std.module.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node

class NodeValueList(tkOperation: Token, override val value: List<Any?>) : Node(tkOperation), IValueNode