package ru.DmN.pht.std.imports.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node

class NodeArgument(tkOperation: Token, override val value: Any?) : Node(tkOperation), IValueNode