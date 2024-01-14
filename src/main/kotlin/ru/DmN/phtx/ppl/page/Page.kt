package ru.DmN.phtx.ppl.page

import ru.DmN.phtx.ppl.Presentation
import java.awt.AlphaComposite
import java.awt.Graphics2D
import javax.swing.JComponent


abstract class Page {
    abstract val component: JComponent
    var blackout: Float = 0f

    open fun onShow(presentation: Presentation) {
        presentation.frame.add(component)
    }

    open fun onHide(presentation: Presentation) {
        presentation.frame.remove(component)
    }

    protected fun Graphics2D.blackout() {
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, blackout)
    }
}