package ru.DmN.phtx.ppl.page.gif

import ru.DmN.phtx.ppl.Presentation
import ru.DmN.phtx.ppl.page.Page
import java.awt.Container
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Image
import java.io.InputStream
import javax.imageio.ImageIO
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.Timer

class GifPage(val image: InputStream, val delay: Int) : Page() {
    private var frame: JFrame? = null
    private val frames = ArrayList<Image>()
    private val timer: Timer
    private var index: Int = 0
    override val component: JComponent = object : JComponent() {
        override fun paint(g: Graphics) {
            g as Graphics2D
            g.blackout()
            g.drawImage(frames[index], 0, 0, width, height, null)
        }
    }

    override fun onShow(presentation: Presentation) {
        frame = presentation.frame
        index = 0
        timer.start()
        super.onShow(presentation)
    }

    override fun onHide(presentation: Presentation) {
        timer.stop()
        index = 0
        frame = null
        super.onHide(presentation)
    }

    init {
        val reader = ImageIO.getImageReadersBySuffix("gif").next()
        val inStream = ImageIO.createImageInputStream(image)
        reader.input = inStream
        var i = 0
        while (i < reader.getNumImages(true)) {
            frames += reader.read(i)
            i++
        }
        timer = Timer(delay) {
            if (index + 1 < frames.size) {
                frame?.repaint()
                index++
            } else index = 0
        }
    }
}