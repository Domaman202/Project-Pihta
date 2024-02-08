package ru.DmN.phtx.ppl.attribute

import ru.DmN.phtx.ppl.element.Element
import java.awt.Dimension
import java.awt.Graphics2D

class AOffset(up: Int, down: Int, left: Int, right: Int, private val element: Element) : Element() {
    private val offset = Offset(up, down, left, right)

    override val type: SizeType
        get() = element.type

    override fun size(w: Dimension, g: Graphics2D): Size =
        element.size(w, g)

    override fun paint(o: Offset, f: Size, w: Dimension, g: Graphics2D) =
        element.paint(o + offset, f.sub(offset.left + offset.right, offset.up + offset.down), w, g)
}