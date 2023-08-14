package ru.DmN.pht.std

import ru.DmN.pht.std.util.IntDownIterator
import ru.DmN.pht.std.util.IntUpIterator

object StdBytecodeAdapter {
    @JvmStatic
    fun range(start: Int, stop: Int): Iterator<Int> = if (start > stop) IntDownIterator(start, stop) else IntUpIterator(start, stop)
}
