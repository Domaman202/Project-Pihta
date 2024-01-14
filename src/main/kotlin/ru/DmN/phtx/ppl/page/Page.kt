package ru.DmN.phtx.ppl.page

import java.awt.AlphaComposite
import java.awt.Graphics2D
import javax.swing.JComponent


abstract class Page {
    abstract val component: JComponent
    @Volatile
    var blackout: Float = 0f

    fun Graphics2D.blackout() {
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, blackout)
    }
}