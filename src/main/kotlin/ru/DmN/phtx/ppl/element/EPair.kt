package ru.DmN.phtx.ppl.element

import ru.DmN.phtx.ppl.element.Element.DrawDirection.DOWN_TO_UP
import ru.DmN.phtx.ppl.element.Element.DrawDirection.UP_TO_DOWN
import ru.DmN.phtx.ppl.element.Element.SizeType.FIXED
import java.awt.Dimension
import java.awt.Graphics2D
import kotlin.math.max

class EPair(val first: Element, val second: Element, override val size: SizeType = FIXED) : Element() {
    override fun paint(dir: DrawDirection, offset: Offset, size: Dimension, g: Graphics2D): Offset {
        if (dir == UP_TO_DOWN) {
            val a = first.paint(UP_TO_DOWN, offset.left(size.width / 2), size, g).up
            val b = second.paint(UP_TO_DOWN, offset.right(size.width / 2), size, g).up
            return offset.up(max(a, b))
        } else {
            val a = first.paint(DOWN_TO_UP, offset.left(size.width / 2), size, g).down
            val b = second.paint(DOWN_TO_UP, offset.right(size.width / 2), size, g).down
            return offset.down(max(a, b))
        }
    }
}