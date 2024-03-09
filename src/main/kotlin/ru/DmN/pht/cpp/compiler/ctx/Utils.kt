package ru.DmN.pht.cpp.compiler.ctx

import ru.DmN.pht.utils.ctx.ContextKeys.OUTPUT
import ru.DmN.pht.utils.ctx.ContextKeys.TESTS
import ru.DmN.siberia.utils.ctx.IContextKey

var MutableMap<IContextKey, Any?>.tests
    set(value) { this[TESTS] = value }
    get() = this[TESTS] as Int

var MutableMap<IContextKey, Any?>.out
    set(value) { this[OUTPUT] = value }
    get() = this[OUTPUT] as StringBuilder
