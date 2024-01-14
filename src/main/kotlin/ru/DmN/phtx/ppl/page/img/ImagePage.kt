package ru.DmN.phtx.ppl.page.img

import ru.DmN.phtx.ppl.page.Page
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Image
import javax.swing.JComponent

class ImagePage(image: Image) : Page() {
    override val component: JComponent =  object : JComponent() {
        override fun paint(g: Graphics) {
            g as Graphics2D
            g.blackout()
            g.drawImage(image, 0, 0, width, height, null)
        }
    }
}