package ru.DmN.pht.std.imports.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node

class NodeValueList(tkOperation: Token, override val value: List<Any?>) : Node(tkOperation), IValueNode