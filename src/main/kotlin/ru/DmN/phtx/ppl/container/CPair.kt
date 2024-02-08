package ru.DmN.phtx.ppl.container

import ru.DmN.phtx.ppl.element.Element
import java.awt.Dimension
import java.awt.Graphics2D

class CPair(private val first: Element, private val second: Element) : Element() {
    override val type: SizeType =
        if (first.type == second.type && second.type == SizeType.FIXED)
            SizeType.FIXED
        else SizeType.DYNAMIC

    override fun size(w: Dimension, g: Graphics2D): Size =
        first.size(w, g) + second.size(w, g)

    override fun paint(o: Offset, f: Size, w: Dimension, g: Graphics2D) {
        val a = w.width / ((o.right + o.left) / 480 + 2)
        val b = f.divWidth()
        first.paint(o.right(a), b, w, g)
        second.paint(o.left(a), b, w, g)
    }
}