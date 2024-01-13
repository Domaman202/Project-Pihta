package ru.DmN.phtx.ppl.page

import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JComponent
import javax.swing.JLabel

class ImagePage(val image: Image) : Page() {
    override val component: JComponent =
        JLabel(ImageIcon(image))
}