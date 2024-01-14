package ru.DmN.phtx.ppl.element

import java.awt.Dimension
import java.awt.Font
import java.awt.Font.BOLD
import java.awt.Font.ITALIC
import java.awt.Graphics2D
import java.awt.RenderingHints.KEY_TEXT_ANTIALIASING
import java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON

class ETitle(private val text: String, private val font: Int) : Element() {
    override val size: SizeType
        get() = SizeType.FIXED

    override fun paint(dir: DrawDirection, offset: Offset, size: Dimension, g: Graphics2D): Offset {
        g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON)
        g.font = Font("TimesRoman", BOLD + ITALIC, font)
        val metrics = g.getFontMetrics(g.font)
        val height = metrics.height
        if (dir == DrawDirection.UP_TO_DOWN) {
            g.drawString(text, (size.width - metrics.stringWidth(text)) / 2, height + offset.up)
            return offset.up(height)
        } else {
            g.drawString(text, (size.width - metrics.stringWidth(text)) / 2,  size.height - height / 2 - offset.down)
            return offset.down(height)
        }
    }
}