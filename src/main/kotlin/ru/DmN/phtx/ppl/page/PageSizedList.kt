package ru.DmN.phtx.ppl.page

import ru.DmN.phtx.ppl.element.Element
import java.awt.AlphaComposite
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_ARGB

class PageSizedList(val width: Int, val height: Int, list: MutableList<Element>) : PageList(list) {
    constructor(width: Int, height: Int) : this(width, height, mutableListOf())

    override fun paint(w: Dimension, g: Graphics2D) {
        val image = BufferedImage(width, height, TYPE_INT_ARGB)
        val graphics = image.graphics as Graphics2D
        graphics.composite = AlphaComposite.Clear
        graphics.fillRect(0, 0, width, height)
        super.paint(Dimension(width, height), graphics)
        g.drawImage(image, 0, 0, w.width, w.height, null)
    }
}