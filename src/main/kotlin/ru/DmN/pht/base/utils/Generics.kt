package ru.DmN.pht.base.utils

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.GlobalContext

class Generics(val list: MutableList<Generic> = ArrayList()) {
    fun getSignature(compiler: Compiler, ctx: GlobalContext): String =
        StringBuilder().apply { list.forEach { append(it.getSignature(compiler, ctx)) } }.toString()

    fun getSignature(compiler: Compiler, ctx: GlobalContext, name: String): String =
        list.find { it.name == name }!!.getSignature(compiler, ctx)

    operator fun get(name: String): String =
        list.find { it.name == name }!!.type
}