package ru.DmN.phtx.ppl

import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfWriter
import ru.DmN.phtx.ppl.page.Page
import java.awt.Color
import java.awt.Font
import java.awt.Font.BOLD
import java.awt.Font.ITALIC
import java.awt.Graphics
import java.awt.Robot
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File
import java.io.FileOutputStream
import java.lang.Thread.sleep
import java.net.URI
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JFrame.EXIT_ON_CLOSE
import javax.swing.JFrame.MAXIMIZED_BOTH
import javax.swing.SwingUtilities
import kotlin.concurrent.thread


class Presentation(title: String, var blackout: Int = 1000) {
    val frame = Frame(title)
    private val pages: MutableList<Page> = ArrayList()
    private var index: Int = -1
    private var updateThread: Thread? = null

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
            pages[prevIndex].onHide(this)
        if (index > -1) {
            val page = pages[index]
            updateThread?.interrupt()
            updateThread = thread {
                try {
                    for (i in 0..blackout) {
                        page.blackout = i / blackout.toFloat()
                        frame.repaint()
                        sleep(1)
                    }
                } catch (_: InterruptedException) {
                }
            }
            page.onShow(this)
        }
        frame.revalidate()
        frame.repaint()
    }

    inner class Frame(title: String) : JFrame(title) {
        override fun paint(g: Graphics) {
            super.paint(g)
            paintPageIndex(g)
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
                VK_P -> {
                    File("dump").mkdir()
                    if (e.modifiersEx and CTRL_DOWN_MASK == CTRL_DOWN_MASK) {
                        SwingUtilities.invokeLater {
                            val oi = index
                            val ob = blackout
                            val od = frame.isUndecorated
                            blackout = 1
                            frame.dispose()
                            frame.isUndecorated = true
                            frame.isVisible = true
                            for (i in oi downTo 0)
                                prevPage()
                            val document = Document(Rectangle(frame.width.toFloat(), frame.height.toFloat()), 0f, 0f, 0f, 0f)
                            PdfWriter.getInstance(document, FileOutputStream("dump/screenshots.pdf"))
                            document.open()
                            for (i in 0 until pages.size) {
                                nextPage()
                                sleep(10)
                                val screenshot = BufferedImage(frame.width, frame.height, TYPE_INT_RGB)
                                frame.paint(screenshot.graphics)
                                ImageIO.write(screenshot, "png", File("dump/$index.png"))
                                document.add(Image.getInstance(screenshot, null))
                            }
                            document.close()
                            frame.dispose()
                            frame.isUndecorated = od
                            frame.isVisible = true
                            blackout = ob
                            for (i in oi..pages.size - 2) {
                                prevPage()
                            }
                        }
                    } else {
                        val screenshot = Robot().createScreenCapture(frame.bounds)
                        ImageIO.write(screenshot, "png", File("dump/$index.png"))
                        println("!")
                    }
                }
            }
        }
    }
}