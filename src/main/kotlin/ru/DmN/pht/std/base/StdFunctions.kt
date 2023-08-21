package ru.DmN.pht.std.base

import ru.DmN.pht.std.base.utils.IModuleObject

object StdFunctions : IModuleObject {
    fun println(vararg args: Any?) {
        for (arg in args)
            print("$arg\t")
        kotlin.io.println()
    }
}