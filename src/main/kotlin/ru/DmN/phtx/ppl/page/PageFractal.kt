package ru.DmN.phtx.ppl.page

import ru.DmN.phtx.ppl.Presentation
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.util.*
import javax.swing.JComponent
import kotlin.concurrent.timer
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class PageFractal : Page() {
    private var s = 0
    private var inc: Boolean = true
    private lateinit var timer: Timer

    override val component = object : JComponent() {
        override fun paint(g: Graphics) {
            super.paint(g)
            g.color = Color(s shl 6 xor s shl 12)
            val startTime = System.currentTimeMillis()
            draw(size.width / 2.0, size.height / 2.0, s, g)
            val time = System.currentTimeMillis() - startTime
            if (time > 100 || s > 500)
                inc = false
            else if (s < 1)
                inc = true
            val string = "[$s][$time]"
            g.font = Font("TimesRoman", Font.BOLD + Font.ITALIC, 24)
            g.color = Color.WHITE
            g.drawString(string, 25, size.height - 25)
        }
    }

    override fun onShow(presentation: Presentation) {
        super.onShow(presentation)
        s = 0
        timer = timer(period = 25) {
            if (inc)
                s++
            else s--
            presentation.frame.repaint()
        }
    }

    override fun onHide(presentation: Presentation) {
        super.onHide(presentation)
        timer.cancel()
    }

    private fun draw(x: Double, y: Double, size: Int, g: Graphics) {
        val n = 6
        val c = 3
        val d = 2
        if (size > 0) {
            val rad = (size / c)
            val den = ((size * (c - 1)).toFloat() / c).roundToInt()
            draw(x, y, rad, g)
            for (i in 0 until n) {
                draw(
                    x - (den * sin(d * PI / n * i)).roundToInt(),
                    y + (den * cos(d * PI / n * i)).roundToInt(),
                    rad,
                    g
                )
            }
            g.drawOval((x-size).toInt(), (y-size).toInt(), 2*size, 2*size)
        }
    }
}