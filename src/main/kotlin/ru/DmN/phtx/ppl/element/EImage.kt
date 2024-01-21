package ru.DmN.phtx.ppl.element

import ru.DmN.phtx.ppl.element.Element.SizeType.DYNAMIC
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Image
import java.awt.RenderingHints.KEY_ANTIALIASING
import java.awt.RenderingHints.VALUE_ANTIALIAS_ON
import java.awt.geom.RoundRectangle2D

class EImage(private val image: Image) : Element() {
    override val type: SizeType
        get() = DYNAMIC

    override fun size(window: Dimension, g: Graphics2D): Size =
        Size(-1, -1)

    override fun paint(offset: Offset, free: Size, w: Dimension, g: Graphics2D) {
        g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
        val x = offset.left + 32
        val y = offset.up + 32
        val sizeX = free.width - 64
        val sizeY = free.height - 64
        g.clip = g.clip.apply {
            g.clip = RoundRectangle2D.Float(x.toFloat(), y.toFloat(), sizeX.toFloat(), sizeY.toFloat(), 50f, 50f)
            g.drawImage(image, x, y, sizeX, sizeY, null)
        }
    }
}