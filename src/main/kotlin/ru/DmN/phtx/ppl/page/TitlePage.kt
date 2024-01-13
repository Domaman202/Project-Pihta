package ru.DmN.phtx.ppl.page

import java.awt.Font
import java.awt.Font.*
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JComponent

class TitlePage(val text: String, val font: Int) : Page() {
    override val component: JComponent = object : JComponent() {
        override fun paintComponent(g: Graphics?) {
            g as Graphics2D
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
            font = Font("TimesRoman", BOLD + ITALIC, this@TitlePage.font)
            val metrics = g.getFontMetrics(font)
            g.drawString(text, (width - metrics.stringWidth(text)) / 2, metrics.height)
        }
    }
}