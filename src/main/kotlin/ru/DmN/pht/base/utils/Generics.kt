package ru.DmN.pht.base.utils

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.std.compiler.java.ctx.GlobalContext
import ru.DmN.pht.std.utils.getSignature

class Generics(val list: MutableList<Generic> = ArrayList()) {
    fun getSignature(compiler: Compiler, ctx: GlobalContext): String =
        StringBuilder().apply { list.forEach { append(it.getSignature(compiler, ctx)) } }.toString()

    operator fun get(name: String): String =
        list.find { it.name == name }!!.type

    companion object {
        val EMPTY = Generics()
    }
}