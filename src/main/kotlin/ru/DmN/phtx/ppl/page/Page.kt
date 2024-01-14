package ru.DmN.phtx.ppl.page

import java.awt.AlphaComposite
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JComponent

abstract class Page {
    var blackout: Float = 0f
    val component = object : JComponent() {
        override fun paint(g: Graphics) {
            this@Page.paint(size, g as Graphics2D)
        }
    }

    open fun paint(size: Dimension, g: Graphics2D) {
        g.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, blackout)
    }
}