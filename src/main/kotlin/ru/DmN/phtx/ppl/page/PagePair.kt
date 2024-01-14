package ru.DmN.phtx.ppl.page

import ru.DmN.phtx.ppl.element.Element
import ru.DmN.phtx.ppl.element.Element.DrawDirection.UP_TO_DOWN
import ru.DmN.phtx.ppl.element.Element.Offset
import java.awt.Dimension
import java.awt.Graphics2D

class PagePair(val first: Element, val second: Element) : Page() {
    override fun paint(size: Dimension, g: Graphics2D) {
        super.paint(size, g)
        first.paint(UP_TO_DOWN, Offset.EMPTY.left(size.width / 2), size, g)
        second.paint(UP_TO_DOWN, Offset.EMPTY.right(size.width / 2), size, g)
    }
}