package ru.DmN.pht.std.utils

object StdOut {
    // todo: other print's
    @JvmStatic
    fun print() = Unit
    @JvmStatic
    fun print(msg: Boolean) = kotlin.io.print(msg)
    @JvmStatic
    fun print(msg: Int) = kotlin.io.print(msg)
    @JvmStatic
    fun print(msg: Any?) = kotlin.io.print(msg)
    @JvmStatic
    fun print(vararg msg: Any?) = kotlin.io.print(msg.contentToString())
    @JvmStatic
    fun println() = kotlin.io.println()
    @JvmStatic
    fun println(msg: Boolean) = kotlin.io.println(msg)
    @JvmStatic
    fun println(msg: Int) = kotlin.io.println(msg)
    @JvmStatic
    fun println(msg: Any?) = kotlin.io.println(msg)
    @JvmStatic
    fun println(vararg msg: Any?) = kotlin.io.println(msg.contentToString())
}