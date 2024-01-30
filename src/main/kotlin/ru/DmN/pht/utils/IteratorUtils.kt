package ru.DmN.pht.utils

object IteratorUtils {
    @JvmStatic
    fun range(start: Int, stop: Int) =
        if (start > stop)
            IntDownIterator(start, stop)
        else IntUpIterator(start, stop)
}