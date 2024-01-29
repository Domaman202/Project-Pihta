package ru.DmN.test

import ru.DmN.pht.std.utils.normalizeName

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        println("set-value".normalizeName())
        println("set-".normalizeName())
    }
}