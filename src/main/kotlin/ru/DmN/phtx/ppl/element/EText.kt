package ru.DmN.phtx.ppl.element

import ru.DmN.phtx.ppl.element.Element.SizeType.FIXED
import java.awt.Dimension
import java.awt.Font
import java.awt.Font.ITALIC
import java.awt.Graphics2D
import java.awt.RenderingHints.KEY_TEXT_ANTIALIASING
import java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON

class EText(private val text: String, private val font: Int) : Element() {
    override val type: SizeType
        get() = FIXED

    override fun size(window: Dimension, g: Graphics2D): Size =
        Size(window.width, g.getFontMetrics(Font("TimesRoman", ITALIC, font)).height * (text.count { it == '\n' } + 3))

    override fun paint(offset: Offset, free: Size, w: Dimension, g: Graphics2D) {
        g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON)
        g.font = Font("TimesRoman", ITALIC, font)
        val height = g.getFontMetrics(g.font).height
        var textY = height
        text.split('\n').forEach {
            textY += height
            g.drawString(it, font + offset.left, textY + offset.up)
        }
    }
}