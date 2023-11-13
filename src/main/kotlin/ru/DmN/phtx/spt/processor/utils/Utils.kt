package ru.DmN.phtx.spt.processor.utils

import ru.DmN.pht.base.utils.IContextCollection
import ru.DmN.phtx.spt.processor.ctx.PageContext
import ru.DmN.phtx.spt.processor.ctx.WindowContext

fun <T : IContextCollection<T>> IContextCollection<T>.with(ctx: WindowContext) =
    this.with("phtx/window", ctx)
fun <T : IContextCollection<T>> IContextCollection<T>.with(ctx: PageContext) =
    this.with("phtx/page", ctx)

fun IContextCollection<*>.isWindow() =
    contexts.containsKey("phtx/window")
fun IContextCollection<*>.isPage() =
    contexts.containsKey("phtx/page")

var IContextCollection<*>.window
    set(value) { contexts["phtx/window"] = value }
    get() = contexts["phtx/window"] as WindowContext
var IContextCollection<*>.page
    set(value) { contexts["phtx/page"] = value }
    get() = contexts["phtx/page"] as PageContext