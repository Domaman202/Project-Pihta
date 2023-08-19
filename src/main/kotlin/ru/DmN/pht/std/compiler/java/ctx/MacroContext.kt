package ru.DmN.pht.std.compiler.java.ctx

import ru.DmN.pht.base.parser.ast.Node

class MacroContext(val args: MutableMap<String, Node> = HashMap()) {
    operator fun get(name: String): Node =
        args[name]!!
}