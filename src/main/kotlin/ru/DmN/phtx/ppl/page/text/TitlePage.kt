package ru.DmN.phtx.ppl.page.text

import ru.DmN.phtx.ppl.page.Page
import java.awt.*
import java.awt.Font.*
import javax.swing.JComponent

class TitlePage(val text: String, val font: Int) : Page() {
    override val component: JComponent = object : JComponent() {
        override fun paintComponent(g: Graphics) {
            g as Graphics2D
            g.blackout()
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
            g.font = Font("TimesRoman", BOLD + ITALIC, this@TitlePage.font)
            val metrics = g.getFontMetrics(g.font)
            g.drawString(text, (width - metrics.stringWidth(text)) / 2, metrics.height)
        }
    }
}