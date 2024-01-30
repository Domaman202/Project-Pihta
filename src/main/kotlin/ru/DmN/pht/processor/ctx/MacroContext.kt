package ru.DmN.pht.processor.ctx

import ru.DmN.siberia.ast.Node
import java.util.UUID

class MacroContext(val args: MutableMap<Pair<UUID, String>, Node> = HashMap()) {
    operator fun set(name: Pair<UUID, String>, value: Node) {
        args[name] = value
    }

    operator fun get(name: Pair<UUID, String>): Node =
        args[name]!!

    fun with(name: Pair<UUID, String>, value: Node): MacroContext =
        MacroContext(args.toMutableMap().apply { this[name] = value })
}