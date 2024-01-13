package ru.DmN.phtx.ppl

import ru.DmN.phtx.ppl.page.Page
import java.awt.Color
import java.awt.Font
import java.awt.Font.BOLD
import java.awt.Font.ITALIC
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.VK_LEFT
import java.awt.event.KeyEvent.VK_RIGHT
import javax.swing.JFrame
import javax.swing.JFrame.EXIT_ON_CLOSE
import javax.swing.JFrame.MAXIMIZED_BOTH
import javax.swing.JPanel

class Presentation(title: String) {
    val frame = Frame(title)
    val pages: MutableList<Page> = ArrayList()
    var index: Int = -1

    fun start() {
        frame.addKeyListener(KeyListener())
        frame.defaultCloseOperation = EXIT_ON_CLOSE
        frame.focusTraversalKeysEnabled = true
        frame.extendedState = MAXIMIZED_BOTH
        frame.isLocationByPlatform = true
        frame.isVisible = true
        nextPage()
    }

    fun nextPage() {
        if (index + 1 < pages.size) {
            index++
            update(index, index - 1)
        }
    }

    fun prevPage() {
        if (index > -1) {
            index--
            update(index, index + 1)
        }
    }

    operator fun plusAssign(page: Page) {
        pages += page
    }

    private fun update(index: Int, prevIndex: Int) {
        if (prevIndex > -1)
            frame.remove(pages[prevIndex].component)
        if (index > -1)
            frame.add(pages[index].component)
        frame.revalidate()
        frame.repaint()
    }

    inner class Frame(title: String) : JFrame(title) {
        override fun paint(g: Graphics?) {
            super.paint(g)
            paintPageIndex(g!!)
        }

        private fun paintPageIndex(g: Graphics) {
            val width = size.width
            val height = size.height
            val string = "($index)"
            g.font = Font("TimesRoman", BOLD + ITALIC, 24)
            g.color = Color.WHITE
            g.drawString(string, width - g.fontMetrics.stringWidth(string) - 25, height - 25)
        }
    }

    inner class KeyListener : KeyAdapter() {
        override fun keyPressed(e: KeyEvent?) {
            when (e!!.keyCode) {
                VK_LEFT -> prevPage()
                VK_RIGHT -> nextPage()
            }
        }
    }
}