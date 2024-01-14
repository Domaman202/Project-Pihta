package ru.DmN.phtx.ppl.element

import java.awt.Dimension
import java.awt.Font
import java.awt.Font.ITALIC
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.RenderingHints.KEY_TEXT_ANTIALIASING
import java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON

class EText(private val text: String, private val font: Int) : Element() {
    override val size: SizeType
        get() = SizeType.FIXED

    override fun paint(dir: DrawDirection, offset: Offset, size: Dimension, g: Graphics2D): Offset {
        g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON)
        g.font = Font("TimesRoman", ITALIC, font)
        val metrics = g.getFontMetrics(g.font)
        val height = metrics.height
        val lines = text.split('\n')
        if (dir == DrawDirection.UP_TO_DOWN) {
            var textY = height
            lines.forEach {
                textY += height
                g.drawString(it, font, textY + offset.up)
            }
            return offset.up(textY)
        } else {
            var textY = size.height
            lines.reversed().forEach {
                textY -= height
                g.drawString(it, font, textY - offset.down)
            }
            return offset.down((lines.size + 1) * height)
        }
    }
}