package ru.DmN.pht.std.module.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node

class NodeValueList(tkOperation: Token, override val value: List<Any?>) : Node(tkOperation), IValueNode