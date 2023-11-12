package ru.DmN.phtx.spt.internal

import java.awt.Graphics
import javax.swing.JPanel

class DrawCallbackJPanel(val callback: (graphics: Graphics) -> Unit) : JPanel() {
    override fun paintComponent(g: Graphics?) {
        g!!
        super.paintComponent(g)
        callback(g)
    }
}