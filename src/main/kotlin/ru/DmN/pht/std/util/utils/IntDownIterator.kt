package ru.DmN.pht.std.util.utils

class IntDownIterator(var i: Int, val end: Int) : Iterator<Int> {
    override fun hasNext(): Boolean =
        i >= end

    override fun next(): Int =
        i--
}