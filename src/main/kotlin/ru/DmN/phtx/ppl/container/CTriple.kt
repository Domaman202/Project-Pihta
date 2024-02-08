package ru.DmN.phtx.ppl.container

import ru.DmN.phtx.ppl.element.Element
import java.awt.Dimension
import java.awt.Graphics2D

class CTriple(private val first: Element, private val second: Element, private val third: Element) : Element() {
    override val type: SizeType =
        if (first.type == second.type && second.type == third.type && third.type == SizeType.FIXED)
            SizeType.FIXED
        else SizeType.DYNAMIC

    override fun size(w: Dimension, g: Graphics2D): Size =
        first.size(w, g) + second.size(w, g) + third.size(w, g)

    override fun paint(o: Offset, f: Size, w: Dimension, g: Graphics2D) {
        val a = (w.width / 1.5).toInt()
        val b = Size(f.width / 3, f.height)
        first.paint(o.right(a), b, w, g)
        second.paint(o.right(a / 2).left(a / 2), b, w, g)
        third.paint(o.left(a), b, w, g)
    }
}