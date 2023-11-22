package ru.DmN.pht.base.processor.utils

import ru.DmN.pht.base.utils.IContextCollection

fun <T : IContextCollection<T>> T.with(ctx: Platform) =
    this.with("base/platform", ctx)
fun <T : IContextCollection<T>> T.withJCV(ctx: Int) =
    this.with("base/jcv", ctx)

var IContextCollection<*>.platform
    set(value) { this.contexts["base/platform"] = value }
    get() = this.contexts["base/platform"] as Platform
var IContextCollection<*>.javaClassVersion
    set(value) { this.contexts["base/jcv"] = value }
    get() = this.contexts["base/jcv"] as Int