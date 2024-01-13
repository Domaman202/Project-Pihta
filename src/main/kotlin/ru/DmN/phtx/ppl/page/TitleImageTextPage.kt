package ru.DmN.phtx.ppl.page

import java.awt.Font
import java.awt.Font.*
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Image
import java.awt.RenderingHints
import java.awt.geom.RoundRectangle2D
import javax.swing.JComponent

class TitleImageTextPage(val title: String, val image: Image, val text: String, val titleFont: Int, val textFont: Int) : Page() {
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
            val textX = lines.map { (width - textMetrics.stringWidth(it)) / 2 }.average().toInt()
            val textHeight = textMetrics.height
            var textY = height - textHeight * (lines.size + 1)
            lines.forEach {
                textY += textHeight
                g.drawString(it, textX, textY)
            }
            val offset = (titleY * 1.5).toInt()
            val imgWidth = width - offset * 2
            val imgHeight = (height - titleY * 2.5) - textHeight * lines.size
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g.setClip(RoundRectangle2D.Double(offset.toDouble(), offset.toDouble(), imgWidth.toDouble(), imgHeight, 50.0, 50.0))
            g.drawImage(image, offset, offset, imgWidth, imgHeight.toInt(), null)
        }
    }
}