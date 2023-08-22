package ru.DmN.pht.example.bf.compiler.java.utils

import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.example.bf.compiler.java.ctx.BFContext

fun CompilationContext.with(ctx: BFContext): CompilationContext =
    this.with("example/bf", ctx)

fun CompilationContext.isBF(): Boolean =
    contexts.containsKey("example/bf")

val CompilationContext.bf
    get() = contexts["example/bf"] as BFContext