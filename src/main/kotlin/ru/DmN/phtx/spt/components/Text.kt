package ru.DmN.phtx.spt.components

import ru.DmN.phtx.spt.utils.DimData
import ru.DmN.phtx.spt.Page
import java.awt.Font
import java.awt.Graphics
import kotlin.math.pow
import kotlin.math.sqrt

class Text(val text: String, private val ix: Int, private val iy: Int, val size: Int) : IComponent {
    private var x = ix
    private var y = iy
    private var ratio = 0f

    override fun draw(page: Page, g: Graphics) {
        val oldFont = g.font
        g.font = Font(g.font.name, Font.BOLD, (size * ratio).toInt())
        g.drawString(text, x, y)
        g.font = oldFont
    }

    override fun resize(page: Page, dim: DimData) {
        val i = (ix * dim.widthRatio)
        x = (if (sqrt(i) > 25) i * 1.06f else i).toInt()
        y = (iy * dim.heightRatio).toInt()
        ratio = dim.ratio.pow(2)
    }
}