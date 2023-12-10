package ru.DmN.phtx.pcl.ast

interface INodeArray {
    val size: Int

    operator fun get(index: Int): NodeElement

    fun forEach(consumer: (it: NodeElement) -> Unit)
}