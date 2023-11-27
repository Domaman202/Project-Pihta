package ru.DmN.siberia.processor.utils

import ru.DmN.siberia.utils.IContextCollection

fun <T : IContextCollection<T>> T.with(ctx: Platform) =
    this.with("siberia/platform", ctx)

/**
 * Платформа
 */
var IContextCollection<*>.platform
    set(value) { this.contexts["siberia/platform"] = value }
    get() = this.contexts["siberia/platform"] as Platform