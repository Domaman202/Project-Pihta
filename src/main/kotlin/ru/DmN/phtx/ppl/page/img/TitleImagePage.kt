package ru.DmN.phtx.ppl.page.img

import ru.DmN.phtx.ppl.page.Page
import java.awt.*
import java.awt.Font.BOLD
import java.awt.Font.ITALIC
import java.awt.geom.RoundRectangle2D
import javax.swing.JComponent

class TitleImagePage(val text: String, val font: Int, val image: Image) : Page() {
    override val component: JComponent = object : JComponent() {
        override fun paintComponent(g: Graphics) {
            g as Graphics2D
            g.blackout()
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
            g.font = Font("TimesRoman", BOLD + ITALIC, this@TitleImagePage.font)
            val metrics = g.getFontMetrics(g.font)
            val titleX = (width - metrics.stringWidth(text)) / 2
            val titleY = metrics.height
            g.drawString(text, titleX, titleY)
            val offset = (titleY * 1.5).toInt()
            val imgWidth = width - offset * 2
            val imgHeight = (height - titleY * 2.5)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g.setClip(RoundRectangle2D.Double(offset.toDouble(), offset.toDouble(), imgWidth.toDouble(), imgHeight, 50.0, 50.0))
            g.drawImage(image, offset, offset, imgWidth, imgHeight.toInt(), null)
        }
    }
}