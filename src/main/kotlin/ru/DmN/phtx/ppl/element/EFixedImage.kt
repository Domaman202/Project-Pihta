package ru.DmN.phtx.ppl.element

import ru.DmN.phtx.ppl.element.Element.DrawDirection.UP_TO_DOWN
import ru.DmN.phtx.ppl.element.Element.SizeType.FIXED
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Image
import java.awt.RenderingHints.KEY_ANTIALIASING
import java.awt.RenderingHints.VALUE_ANTIALIAS_ON
import java.awt.geom.RoundRectangle2D

class EFixedImage(private val image: Image, private val height: Float) : Element() {
    override val size: SizeType
        get() = FIXED

    override fun paint(dir: DrawDirection, offset: Offset, size: Dimension, g: Graphics2D): Offset {
        if (dir == UP_TO_DOWN) {
            val x = offset.left + 32
            val y = offset.up + 32
            val sizeX = size.width - 64
            val sizeY = (size.height * height).toInt() - 42
            g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
            g.clip = RoundRectangle2D.Float(x.toFloat(), y.toFloat(), sizeX.toFloat(), sizeY.toFloat(), 50f, 50f)
            g.drawImage(image, x, y, sizeX, sizeY, null)
            return offset.up(sizeY + 32)
        } else TODO()
    }
}