package ru.DmN.pht.std.base.compiler.java.utils

class SubList<E>(val parent: List<E>, val list: MutableList<E> = ArrayList()) : MutableList<E> {
    override val size: Int
        get() = list.size + parent.size

    override fun clear() =
        list.clear()

    override fun addAll(elements: Collection<E>): Boolean =
        list.addAll(elements)

    override fun addAll(index: Int, elements: Collection<E>): Boolean =
        throw UnsupportedOperationException("Not yet implemented")

    override fun add(index: Int, element: E) =
        throw UnsupportedOperationException("Not yet implemented")

    override fun add(element: E): Boolean =
        list.add(element)

    override fun get(index: Int): E =
        if (index < list.size)
            list[index]
        else parent[index - list.size]

    override fun isEmpty(): Boolean =
        list.isEmpty() && parent.isEmpty()

    override fun iterator(): MutableIterator<E> =
        Iterator()

    override fun listIterator(): MutableListIterator<E> =
        throw UnsupportedOperationException("Not yet implemented")

    override fun listIterator(index: Int): MutableListIterator<E> =
        throw UnsupportedOperationException("Not yet implemented")

    override fun removeAt(index: Int): E =
        throw UnsupportedOperationException("Not yet implemented")

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> =
        throw UnsupportedOperationException("Not yet implemented")

    override fun set(index: Int, element: E): E =
        throw UnsupportedOperationException("Not yet implemented")

    override fun retainAll(elements: Collection<E>): Boolean =
        throw UnsupportedOperationException("Not yet implemented")

    override fun removeAll(elements: Collection<E>): Boolean =
        throw UnsupportedOperationException("Not yet implemented")

    override fun remove(element: E): Boolean =
        throw UnsupportedOperationException("Not yet implemented")

    override fun lastIndexOf(element: E): Int =
        throw UnsupportedOperationException("Not yet implemented")

    override fun indexOf(element: E): Int =
        throw UnsupportedOperationException("Not yet implemented")

    override fun containsAll(elements: Collection<E>): Boolean =
        elements.filter(::contains).size == elements.size

    override fun contains(element: E): Boolean =
        list.contains(element) || parent.contains(element)

    inner class Iterator(var i: Int = 0) : MutableIterator<E> {
        override fun hasNext(): Boolean =
            i < this@SubList.size

        override fun next(): E =
            this@SubList[i++]

        override fun remove() =
            throw UnsupportedOperationException("Not yet implemented")
    }
}