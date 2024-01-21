package ru.DmN.phtx.ppl.element

import java.awt.Dimension
import java.awt.Graphics2D

class EPair(val first: Element, val second: Element) : Element() {
    override val type: SizeType =
        if (first.type == second.type && second.type == SizeType.FIXED) SizeType.FIXED else SizeType.DYNAMIC

    override fun size(w: Dimension, g: Graphics2D): Size =
        if (type == SizeType.FIXED)
            first.size(w, g) + second.size(w, g)
        else Size(-1, -1)

    override fun paint(offset: Offset, free: Size, w: Dimension, g: Graphics2D) {
        val a = w.width / ((offset.right + offset.left) / 480 + 2)
        val b = free.divWidth()
        first.paint(offset.right(a), b, w, g)
        second.paint(offset.left(a), b, w, g)
    }
}