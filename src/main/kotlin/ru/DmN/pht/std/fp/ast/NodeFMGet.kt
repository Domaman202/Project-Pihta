package ru.DmN.pht.std.fp.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.std.value.ast.NodeGetOrName

class NodeFMGet(tkOperation: Token, val instance: Node, name: String, static: Boolean) : NodeGetOrName(tkOperation, name, static)