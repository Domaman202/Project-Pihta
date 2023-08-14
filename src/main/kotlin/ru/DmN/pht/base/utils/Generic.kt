package ru.DmN.pht.base.utils

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.GlobalContext

class Generic(val name: String, val type: String, var extends: String? = null) {
    fun getSignature(compiler: Compiler, ctx: GlobalContext): String =
        "${name}:${ctx.getType(compiler, type).desc}"
}