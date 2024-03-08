package ru.DmN.pht.cpp.compiler.ctx

import ru.DmN.pht.utils.ctx.ContextKeys.OUTPUT
import ru.DmN.siberia.utils.ctx.IContextKey

var MutableMap<IContextKey, Any?>.out
    set(value) { this[OUTPUT] = value }
    get() = this[OUTPUT] as StringBuilder