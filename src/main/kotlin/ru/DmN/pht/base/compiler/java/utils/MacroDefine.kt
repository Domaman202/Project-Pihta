package ru.DmN.pht.base.compiler.java.utils

import ru.DmN.pht.base.compiler.java.ctx.GlobalContext
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList

class MacroDefine(val name: String, val args: List<String>, val body: List<Node>, val ctx: GlobalContext) {
    fun toNodesList(): NodeNodesList =
        NodeNodesList(Token(-1, Token.Type.OPERATION, "progn"), body.toMutableList())
}