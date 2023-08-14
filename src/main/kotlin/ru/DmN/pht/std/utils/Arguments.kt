package ru.DmN.pht.std.utils

import ru.DmN.pht.base.utils.Klass
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.base.utils.klassOf

class Arguments(val list: List<Pair<String, String>> = ArrayList()) : Iterable<Pair<String, Klass>> {
    val size: Int
        get() = list.size
    val desc: String
        get() {
            val str = StringBuilder()
            list.forEach { str.append(it.second.desc) }
            return str.toString()
        }
    fun isVarArgs(): Boolean =
        list.isNotEmpty() && list.last().first.startsWith("*")
    operator fun get(index: Int) = list[index]

    fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append("[args")
        if (list.isNotEmpty())
            builder.append('\n')
        list.forEach { builder.indent(indent + 1).append(it.first).append('\t').append(it.second).append('\n') }
        if (list.isNotEmpty())
            builder.indent(indent)
        return builder.append(']')
    }

    override fun iterator(): Iterator<Pair<String, Klass>> = list.map { Pair(it.first, klassOf(it.second)) }.iterator()
}