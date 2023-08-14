package ru.DmN.pht.std

import ru.DmN.pht.std.utils.IModuleObject

object StdFunctions : IModuleObject {
    fun println(vararg args: Any?) {
        for (arg in args)
            print("$arg\t")
        kotlin.io.println()
    }
}