package ru.DmN.phtx.pcl.ast

interface INodeArray {
    operator fun get(index: Int): NodeElement
}