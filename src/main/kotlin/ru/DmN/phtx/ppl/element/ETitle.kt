package ru.DmN.phtx.ppl.element

import ru.DmN.phtx.ppl.element.Element.SizeType.FIXED
import java.awt.Dimension
import java.awt.Font
import java.awt.Font.BOLD
import java.awt.Font.ITALIC
import java.awt.Graphics2D
import java.awt.RenderingHints.KEY_TEXT_ANTIALIASING
import java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON

class ETitle(private val text: String, private val font: Int) : Element() {
    override val type: SizeType
        get() = FIXED

    override fun size(w: Dimension, g: Graphics2D): Size =
        Size(w.width, g.getFontMetrics(Font("TimesRoman", BOLD + ITALIC, font)).height)

    override fun paint(o: Offset, f: Size, w: Dimension, g: Graphics2D) {
        g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON)
        g.font = Font("TimesRoman", BOLD + ITALIC, font)
        val metrics = g.getFontMetrics(g.font)
        g.drawString(text, (w.width - metrics.stringWidth(text) + o.right - o.left) / 2, metrics.height + o.up)
    }
}