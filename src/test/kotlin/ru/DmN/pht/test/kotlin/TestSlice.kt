package ru.DmN.pht.test.kotlin

object TestSlice {
    @JvmStatic
    fun main(args: Array<String>) {
        val list = ArrayList(listOf(1, 2, 3, 555, 4, 5, 6))
        sliceInsert(list, 3, listOf(0, 0, 0))
        println(list)
    }

    fun <T> sliceInsert(list: MutableList<T?>, index: Int, elements: List<T>) {
        val right = list.subList(index + 1, list.size).toList()
        for (i in list.size until elements.size + index + right.size)
            list.add(null)
        elements.forEachIndexed { i, it -> list[index + i] = it }
        right.forEachIndexed { i, it -> list[index + i + elements.size] = it }
    }
}