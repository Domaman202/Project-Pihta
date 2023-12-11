package ru.DmN.pht.std.test

object RandomTests {
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            throw Exception()
        } catch (e: Exception) {
            println(e)
        }
    }
}