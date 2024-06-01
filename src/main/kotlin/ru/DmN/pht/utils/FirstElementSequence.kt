package ru.DmN.pht.utils

class FirstElementSequence<T>(private val iterable: Iterable<T>, private val first: T) : Sequence<T> {
    override fun iterator(): Iterator<T> =
        FirstElementIterator(iterable.iterator(), first)

    class FirstElementIterator<T>(private val parent: Iterator<T>, private var first: T?): Iterator<T> {
        override fun hasNext(): Boolean =
            first != null || parent.hasNext()

        override fun next(): T {
            val first = this.first ?: return this.parent.next()
            this.first = null
            return first
        }
    }
}