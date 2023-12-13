package ru.DmN.pht.std.utils

object IteratorUtils {
    @JvmStatic
    fun range(start: Int, stop: Int) =
        if (start > stop)
            IntDownIterator(start, stop)
        else IntUpIterator(start, stop)
}