package ru.DmN.pht.parser.utils

import ru.DmN.pht.utils.ctx.ContextKeys
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.utils.ctx.IContextCollection
import java.util.*

fun IContextCollection<*>.clearMacros() {
    this.contexts.remove(ContextKeys.MACROS)
}

var IContextCollection<*>.macros
    set(value) { this.contexts[ContextKeys.MACROS] = value }
    get() = this.contexts[ContextKeys.MACROS] as Stack<UUID>