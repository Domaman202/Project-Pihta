package ru.DmN.pht.utils

class IntUpIterator(private var i: Int, private val end: Int) : Iterator<Int> {
    override fun hasNext(): Boolean =
        i <= end

    override fun next(): Int =
        i++
}