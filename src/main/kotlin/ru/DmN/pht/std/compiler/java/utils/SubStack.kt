package ru.DmN.pht.std.compiler.java.utils

import java.util.Stack

class SubStack<E>(val parent: Stack<E>) : Stack<E>() {
    override val size: Int
        get() = super.size + parent.size

    override fun peek(): E =
        if (super.isEmpty())
            parent.peek()
        else super.peek()

    override fun pop(): E =
        if (super.isEmpty())
            parent.pop()
        else super.pop()

    override fun get(index: Int): E =
        if (index < parent.size)
            parent[index]
        else parent[index - parent.size]

    override fun isEmpty(): Boolean =
        super.isEmpty() && parent.isEmpty()

    override fun iterator(): MutableIterator<E> =
        Iterator()

    override fun containsAll(elements: Collection<E>): Boolean =
        elements.filter(::contains).size == elements.size

    override fun contains(element: E): Boolean =
        super.contains(element) || parent.contains(element)

    inner class Iterator(var i: Int = 0) : MutableIterator<E> {
        override fun hasNext(): Boolean =
            i < this@SubStack.size

        override fun next(): E =
            this@SubStack[i++]

        override fun remove() =
            throw UnsupportedOperationException("Not yet implemented")
    }
}