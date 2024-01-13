package ru.DmN.phtx.ppl.page

import java.awt.Font
import java.awt.Font.*
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Image
import java.awt.RenderingHints
import java.awt.geom.RoundRectangle2D
import javax.swing.JComponent

class TitleImageTextPage(val title: String, val image: Image, val text: String, val titleFont: Int, val textFont: Int, val imageUp: Boolean) : Page() {
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
            if (imageUp) {
                var textY = height - textHeight * (lines.size + 1)
                lines.forEach {
                    textY += textHeight
                    g.drawString(it, textFont, textY)
                }
                val offset = titleY * 1.5
                val imgWidth = width - offset * 2
                val imgHeight = height - titleY * 2.5 - textHeight * lines.size
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                g.setClip(RoundRectangle2D.Double(offset, offset, imgWidth, imgHeight, 50.0, 50.0))
                g.drawImage(image, offset.toInt(), offset.toInt(), imgWidth.toInt(), imgHeight.toInt(), null)
            } else {
                var textY = height - textHeight * (lines.size + 1)
                lines.reversed().forEach {
                    textY += textHeight
                    g.drawString(it, textFont, height - textY + titleY)
                }
                val imgX = titleY * 1.5
                val imgY = textHeight * (lines.size + 2)
                val imgWidth = width - imgX * 2
                val imgHeight = height - imgY - textHeight
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                g.setClip(RoundRectangle2D.Double(imgX, imgY.toDouble(), imgWidth, imgHeight.toDouble(), 50.0, 50.0))
                g.drawImage(image, imgX.toInt(), imgY, imgWidth.toInt(), imgHeight, null)
            }
        }
    }
}