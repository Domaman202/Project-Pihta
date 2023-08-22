package ru.DmN.pht.test.bf.compiler.java.utils

import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.test.bf.compiler.java.ctx.BFContext

fun CompilationContext.with(ctx: BFContext): CompilationContext =
    this.with("test/bf", ctx)

fun CompilationContext.isBF(): Boolean =
    contexts.containsKey("test/bf")

val CompilationContext.bf
    get() = contexts["test/bf"] as BFContext