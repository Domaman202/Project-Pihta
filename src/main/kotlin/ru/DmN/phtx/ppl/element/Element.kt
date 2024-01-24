package ru.DmN.phtx.ppl.element

import java.awt.Dimension
import java.awt.Graphics2D

abstract class Element {
    abstract val type: SizeType
    abstract fun size(w: Dimension, g: Graphics2D): Size
    abstract fun paint(offset: Offset, free: Size, w: Dimension, g: Graphics2D)

    enum class SizeType {
        FIXED,
        DYNAMIC
    }

    data class Size(
        val width: Int,
        val height: Int
    ) {
        operator fun plus(other: Size) =
            Size(width + other.width, height + other.height)
        fun sub(width: Int, height: Int) =
            Size(this.width - width, this.height - height)
        fun divWidth(i: Int = 2): Size =
            Size(width / i, height)
        fun divHeight(i: Int = 2): Size =
            Size(width, height / i)
    }

    data class Offset(
        val up: Int,
        val down: Int,
        val left: Int,
        val right: Int
    ) {

        fun up(up: Int) =
            Offset(up + this.up, down, left, right)
        fun down(down: Int) =
            Offset(up, down + this.down, left, right)
        fun left(left: Int) =
            Offset(up, down, left + this.left, right)
        fun right(right: Int) =
            Offset(up, down, left, right + this.right)

        fun offset(up: Int, down: Int, left: Int, right: Int) =
            Offset(up + this.up, down + this.down, left + this.left, right + this.right)
        
        fun offset(offset: Offset) =
            Offset(up + offset.up, down + offset.down, left + offset.left, right + offset.right)
        fun offset(size: Size) =
            up(size.height)

        companion object {
            val EMPTY = Offset(0, 0, 0, 0)
        }
    }
}