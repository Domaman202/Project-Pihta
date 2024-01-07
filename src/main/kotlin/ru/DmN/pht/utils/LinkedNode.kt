package ru.DmN.pht.utils

open class LinkedNode<T> : Iterable<T> {
    private val prev: LinkedNode<T>
    val element: T

    constructor(prev: LinkedNode<T>, element: T) {
        this.prev = prev
        this.element = element
    }

    private constructor(element: T) {
        this.prev = this
        this.element = element
    }

    override fun iterator(): Iterator<T> =
        object : Iterator<T> {
            var node = this@LinkedNode

            override fun next(): T {
                val value = node.element
                node = node.prev
                return value
            }

            override fun hasNext(): Boolean =
                node != LinkedNodeStart
        }

    object LinkedNodeStart : LinkedNode<Unit>(Unit)
}