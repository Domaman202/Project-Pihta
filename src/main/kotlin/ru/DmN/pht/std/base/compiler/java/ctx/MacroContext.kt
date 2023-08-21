package ru.DmN.pht.std.base.compiler.java.ctx

import ru.DmN.pht.base.parser.ast.Node

class MacroContext(val args: MutableMap<String, Node> = HashMap()) {
    operator fun set(name: String, value: Node) {
        args[name] = value
    }

    operator fun get(name: String): Node =
        args[name]!!

    fun with(name: String, value: Node): MacroContext =
        MacroContext(args.toMutableMap().apply { this[name] = value })
}