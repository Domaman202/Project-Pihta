package ru.DmN.phtx.pls.processor.utils

import ru.DmN.pht.std.ast.NodeModifierNodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token

fun nodePrognB(line: Int, nodes: MutableList<Node>) =
    NodeModifierNodesList(Token.operation(-1, "progn-"), nodes)