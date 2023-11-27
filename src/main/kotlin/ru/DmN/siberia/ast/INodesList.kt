package ru.DmN.siberia.ast

/**
 * Интерфейс описывающий ноду, имеющую под-ноды.
 */
interface INodesList {
    val nodes: MutableList<Node>
}