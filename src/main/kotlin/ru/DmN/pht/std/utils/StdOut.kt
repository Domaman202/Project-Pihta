package ru.DmN.pht.std.utils

import kotlin.io.print as kprint
import kotlin.io.println as kprintln

object StdOut {
    @JvmStatic
    fun print() =
        Unit
    @JvmStatic
    fun print(msg: Boolean) =
        kprint(msg)
    @JvmStatic
    fun print(msg: Byte) =
        kprint(msg)
    @JvmStatic
    fun print(msg: Short) =
        kprint(msg)
    @JvmStatic
    fun print(msg: Char) =
        kprint(msg)
    @JvmStatic
    fun print(msg: Int) =
        kprint(msg)
    @JvmStatic
    fun print(msg: Long) =
        kprint(msg)
    @JvmStatic
    fun print(msg: Float) =
        kprint(msg)
    @JvmStatic
    fun print(msg: Double) =
        kprint(msg)
    @JvmStatic
    fun print(msg: Any?) =
        kprint(msg)
    @JvmStatic
    fun print(vararg msg: Any?) =
        kprint(msg.contentToString())
    @JvmStatic
    fun println() =
        kprintln()
    @JvmStatic
    fun println(msg: Boolean) =
        kprintln(msg)
    @JvmStatic
    fun println(msg: Byte) =
        kprintln(msg)
    @JvmStatic
    fun println(msg: Short) =
        kprintln(msg)
    @JvmStatic
    fun println(msg: Char) =
        kprintln(msg)
    @JvmStatic
    fun println(msg: Int) =
        kprintln(msg)
    @JvmStatic
    fun println(msg: Long) =
        kprintln(msg)
    @JvmStatic
    fun println(msg: Float) =
        kprintln(msg)
    @JvmStatic
    fun println(msg: Double) =
        kprintln(msg)
    @JvmStatic
    fun println(msg: Any?) =
        kprintln(msg)
    @JvmStatic
    fun println(vararg msg: Any?) =
        kprintln(msg.contentToString())
}