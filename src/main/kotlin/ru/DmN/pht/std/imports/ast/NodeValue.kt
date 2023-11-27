package ru.DmN.pht.std.imports.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node

class NodeValue(tkOperation: Token, override val value: String) : Node(tkOperation), IValueNode