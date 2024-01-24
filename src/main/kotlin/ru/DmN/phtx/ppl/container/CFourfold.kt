package ru.DmN.phtx.ppl.container

import ru.DmN.phtx.ppl.element.Element
import java.awt.Dimension
import java.awt.Graphics2D

class CFourfold(private val first: Element, private val second: Element, private val third: Element, private val fourth: Element) : Element() {
    override val type: SizeType =
        if (first.type == second.type && second.type == third.type && third.type == SizeType.FIXED)
            SizeType.FIXED
        else SizeType.DYNAMIC

    override fun size(w: Dimension, g: Graphics2D): Size =
        first.size(w, g) + second.size(w, g) + third.size(w, g)

    override fun paint(offset: Offset, free: Size, w: Dimension, g: Graphics2D) {
        val a = w.width / ((offset.right + offset.left) / 480 + 2)
        val b = free.divWidth().divHeight()
        val right = offset.right(a)
        val left = offset.left(a)
        first.paint(right, b, w, g)
        second.paint(left, b, w, g)
        third.paint(right.up(b.height), b, w, g)
        fourth.paint(left.up(b.height), b, w, g)
    }
}