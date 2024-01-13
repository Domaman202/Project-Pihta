package ru.DmN.phtx.ppl.page

import java.awt.*
import java.awt.Font.BOLD
import java.awt.Font.ITALIC
import java.awt.geom.RoundRectangle2D
import javax.swing.JComponent

class TitleImageImagePage(val text: String, val font: Int, val image0: Image, val image1: Image) : Page() {
    override val component: JComponent = object : JComponent() {
        override fun paintComponent(g: Graphics?) {
            g as Graphics2D
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
            g.font = Font("TimesRoman", BOLD + ITALIC, this@TitleImageImagePage.font)
            val metrics = g.getFontMetrics(g.font)
            val titleX = (width - metrics.stringWidth(text)) / 2
            val titleY = metrics.height
            g.drawString(text, titleX, titleY)
            val offset = (titleY * 1.5).toInt()
            val imgWidth = width / 2 - offset * 1.5
            val imgHeight = (height - titleY * 2.5)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g.setClip(RoundRectangle2D.Double(offset.toDouble(), offset.toDouble(), imgWidth, imgHeight, 50.0, 50.0))
            g.drawImage(image0, offset, offset, imgWidth.toInt(), imgHeight.toInt(), null)
            val imgX = offset * 2 + imgWidth
            g.setClip(RoundRectangle2D.Double(imgX, offset.toDouble(), imgWidth, imgHeight, 50.0, 50.0))
            g.drawImage(image1, imgX.toInt(), offset, imgWidth.toInt(), imgHeight.toInt(), null)
        }
    }
}