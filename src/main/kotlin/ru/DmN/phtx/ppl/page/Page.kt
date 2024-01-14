package ru.DmN.phtx.ppl.page

import ru.DmN.phtx.ppl.Presentation
import java.awt.AlphaComposite
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JComponent

abstract class Page {
    var blackout: Float = 0f
    open val component = object : JComponent() {
        override fun paint(g: Graphics) {
            this@Page.paint(size, g as Graphics2D)
        }
    }

    open fun onShow(presentation: Presentation) {
        presentation.frame.add(component)
    }

    open fun onHide(presentation: Presentation) {
        presentation.frame.remove(component)
    }

    open fun paint(size: Dimension, g: Graphics2D) {
        g.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, blackout)
    }
}