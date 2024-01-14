package ru.DmN.phtx.ppl.element

import java.awt.Dimension
import java.awt.Graphics2D

abstract class Element {
    abstract val size: SizeType

    abstract fun paint(dir: DrawDirection, offset: Offset, size: Dimension, g: Graphics2D): Offset

    enum class SizeType {
        FIXED,
        DYNAMIC
    }

    enum class DrawDirection {
        UP_TO_DOWN,
        DOWN_TO_UP
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

        companion object {
            val EMPTY = Offset(0, 0, 0, 0)
        }
    }
}