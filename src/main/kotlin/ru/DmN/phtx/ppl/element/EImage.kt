package ru.DmN.phtx.ppl.element

import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Image
import java.awt.RenderingHints.KEY_ANTIALIASING
import java.awt.RenderingHints.VALUE_ANTIALIAS_ON
import java.awt.geom.RoundRectangle2D

class EImage(private val image: Image) : Element() {
    override val size: SizeType
        get() = SizeType.DYNAMIC

    override fun paint(dir: DrawDirection, offset: Offset, size: Dimension, g: Graphics2D): Offset {
        if (dir == DrawDirection.UP_TO_DOWN) {
            val x = offset.left + 32
            val y = offset.up + 32
            val sizeX = size.width - offset.left - offset.right - 64
            val sizeY = size.height - offset.up - offset.down - 64
            g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
            g.clip = RoundRectangle2D.Float(x.toFloat(), y.toFloat(), sizeX.toFloat(), sizeY.toFloat(), 50f, 50f)
            g.drawImage(image, x, y, sizeX, sizeY, null)
            return offset.up(sizeY)
        } else TODO()
    }
}