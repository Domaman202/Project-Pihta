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
                val value = node.element
                node = node.prev
                return value
            }

            override fun hasNext(): Boolean =
                node != LinkedClassesNodeStart
        }

    object LinkedClassesNodeStart : LinkedClassesNode<Unit>(Unit)
}