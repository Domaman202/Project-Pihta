package ru.DmN.phtx.spt

import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.JFrame

class Window(val width: Int, val height: Int) {
    val jframe = JFrame()
    val pages: MutableList<Page> = ArrayList()
    var pageIndex = -1

    init {
        jframe.setSize(width, height)
        jframe.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                if (pageIndex > -1) {
                    e!!
                    pages[pageIndex].resize(jframe.width, jframe.height)
                }
            }
        })
    }

    fun newPage(): Page =
        Page(this).apply { pages += this }

    fun showNext() {
        jframe.isVisible = false
        pages[++pageIndex].show()
        jframe.isVisible = true
    }
}