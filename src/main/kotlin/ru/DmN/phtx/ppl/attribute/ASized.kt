package ru.DmN.phtx.ppl.attribute

import ru.DmN.phtx.ppl.element.Element
import java.awt.Dimension
import java.awt.Graphics2D

class ASized(private val sizeX: Int, private val sizeY: Int, private val element: Element) : Element() {
    override val type: SizeType
        get() = element.type

    override fun size(w: Dimension, g: Graphics2D): Size =
        element.size(w, g).run { Size(width, height) }

    override fun paint(o: Offset, f: Size, w: Dimension, g: Graphics2D) {
        lateinit var free: Size
        lateinit var window: Dimension
        if (sizeX > -1) {
            if (sizeY > -1) {
                free = Size(sizeX, sizeY)
                window = Dimension(sizeX, sizeY)
            } else {
                free = Size(sizeX, f.height)
                window = Dimension(sizeX, w.height)
            }
        } else if (sizeY > -1) {
            free = Size(f.width, sizeY)
            window = Dimension(w.width, sizeY)
        } else {
            free = Size(f.width, f.height)
            window = Dimension(w.width, w.height)
        }
        element.paint(
            o,
            free,
            window,
            g
        )
    }
}