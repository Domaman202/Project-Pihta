package ru.DmN.phtx.ppl.page

import java.awt.Font
import java.awt.Font.*
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JComponent

class TitleTextPage(val title: String, val text: String, val titleFont: Int, val textFont: Int) : Page() {
    override val component: JComponent = object : JComponent() {
        override fun paintComponent(g: Graphics?) {
            g as Graphics2D
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
            g.font = Font("TimesRoman", BOLD + ITALIC, titleFont)
            val titleMetrics = g.getFontMetrics(g.font)
            val titleX = (width - titleMetrics.stringWidth(title)) / 2
            val titleY = titleMetrics.height
            g.drawString(title, titleX, titleY)
            g.font = Font("TimesRoman", ITALIC, textFont)
            val textMetrics = g.getFontMetrics(g.font)
            val lines = text.split('\n')
            val textHeight = textMetrics.height
            var textY = height - textHeight * (lines.size + 1)
            lines.forEach {
                textY += textHeight
                g.drawString(it, textFont, textY)
            }
        }
    }
}