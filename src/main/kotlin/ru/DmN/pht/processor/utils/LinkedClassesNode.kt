package ru.DmN.pht.processor.utils

open class LinkedClassesNode<T> : Iterable<T> {
    private val prev: LinkedClassesNode<T>
    val element: T

    constructor(prev: LinkedClassesNode<T>, element: T) {
        this.prev = prev
        this.element = element
    }

    private constructor(element: T) {
        this.prev = this
        this.element = element
    }

    override fun iterator(): Iterator<T> =
        object : Iterator<T> {
            var node = this@LinkedClassesNode

            override fun next(): T {
                node = node.prev
                val value = node.element
                return value
            }

            override fun hasNext(): Boolean =
                node.prev != LinkedClassesNodeStart
        }

    object LinkedClassesNodeStart : LinkedClassesNode<Unit>(Unit)
}