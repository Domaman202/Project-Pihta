package ru.DmN.pht.std.parser

import ru.DmN.pht.base.utils.IContextCollection
import java.util.Stack
import java.util.UUID

var IContextCollection<*>.macros
    set(value) { this.contexts["pht/macro"] = value }
    get() = this.contexts["pht/macro"] as Stack<UUID>