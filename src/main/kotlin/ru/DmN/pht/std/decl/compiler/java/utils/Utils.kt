package ru.DmN.pht.std.decl.compiler.java.utils

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Generic
import ru.DmN.pht.base.utils.Generics
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.ctx.GlobalContext

fun VirtualType.getType(compiler: Compiler, gctx: GlobalContext, name: String): VirtualType {
    var generic: String? = null
    val type = gctx.getType(compiler, name.run {
        if (endsWith('^'))
            generics[substring(0, name.length - 1)]
        else indexOf('<').run {
            if (this == -1)
                name
            else {
                generic = name.substring(this + 1, name.length - 1).let { if (it.endsWith('^')) it else getType(compiler, gctx, it.substring(1)).name }
                name.substring(0, this)
            }
        }
    })
    return if (generic == null)
        type
    else {
        val generics = generic!!.split('^').dropLast(1)
        type.with(Generics(ArrayList<Generic>().apply {
            generics.forEachIndexed { i, it ->
                if (i > this.size - 1)
                    this += Generic("PHT$$i", getType(compiler, gctx, "$it^").desc)
                this[i].extends = "T${it};"
            }
        }))
    }
}

fun CompilationContext.with(ctx: VirtualType) =
    this.with("std/decl/clazz", ctx)

fun CompilationContext.isClass() =
    contexts.containsKey("std/decl/clazz")

val CompilationContext.clazz
    get() = contexts["std/decl/clazz"] as VirtualType