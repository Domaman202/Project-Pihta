package ru.DmN.siberia.compiler.utils

import ru.DmN.siberia.compiler.ctx.CompilationContext

fun CompilationContext.withJCV(ctx: Int) =
    this.with("siberia/jcv", ctx)

/**
 * Версия java классив
 */
var CompilationContext.javaClassVersion
    set(value) { this.contexts["siberia/jcv"] = value }
    get() = this.contexts["siberia/jcv"] as Int