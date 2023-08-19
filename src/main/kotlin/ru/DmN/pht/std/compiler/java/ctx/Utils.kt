package ru.DmN.pht.std.compiler.java.ctx

import ru.DmN.pht.base.compiler.java.ctx.CompilationContext

fun CompilationContext.with(ctx: GlobalContext): CompilationContext =
    this.with("std/global", ctx)
fun CompilationContext.with(ctx: EnumContext): CompilationContext =
    this.with("std/enum", ctx)
fun CompilationContext.with(ctx: ClassContext): CompilationContext =
    this.with("std/class", ctx)
fun CompilationContext.with(ctx: MethodContext): CompilationContext =
    this.with("std/method", ctx)
fun CompilationContext.with(ctx: BodyContext): CompilationContext =
    this.with("std/body", ctx)
fun CompilationContext.with(ctx: MacroContext): CompilationContext =
    this.with("std/macro", ctx)

fun CompilationContext.isGlobal(): Boolean =
    contexts.containsKey("std/global")
fun CompilationContext.isEnum(): Boolean =
    contexts.containsKey("std/enum")
fun CompilationContext.isClass(): Boolean =
    contexts.containsKey("std/class") || isEnum()
fun CompilationContext.isMethod(): Boolean =
    contexts.containsKey("std/method")
fun CompilationContext.isBody(): Boolean =
    contexts.containsKey("std/body")
fun CompilationContext.isMacro(): Boolean =
    contexts.containsKey("std/macro")

val CompilationContext.global
    get() = contexts["std/global"] as GlobalContext
val CompilationContext.enum
    get() = contexts["std/enum"] as EnumContext
val CompilationContext.clazz
    get() = contexts["std/class"] as ClassContext
val CompilationContext.method
    get() = contexts["std/method"] as MethodContext
val CompilationContext.body
    get() = contexts["std/body"] as BodyContext
val CompilationContext.macro
    get() = contexts["std/macro"] as MacroContext