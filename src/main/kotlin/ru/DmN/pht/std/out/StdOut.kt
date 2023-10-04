package ru.DmN.pht.std.out

import ru.DmN.pht.std.base.ups.NUPDefault
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.out.processors.NRPrint

object StdOut : StdModule("std/out") {
    init {
        add("print",    NUPDefault, NRPrint)
        add("println",  NUPDefault, NRPrint)
    }

    // todo: other print's

    @JvmStatic
    fun print() = Unit
    @JvmStatic
    fun print(i: Int) = kotlin.io.print(i)
    @JvmStatic
    fun print(obj: Any?) = kotlin.io.print(obj)
    @JvmStatic
    fun println() = kotlin.io.println()
    @JvmStatic
    fun println(i: Int) = kotlin.io.println(i)
    @JvmStatic
    fun println(obj: Any?) = kotlin.io.println(obj)
}