package ru.DmN.phtx.spt.components

import ru.DmN.phtx.spt.DimData
import ru.DmN.phtx.spt.Page
import java.awt.Font
import java.awt.Graphics
import kotlin.math.pow
import kotlin.math.sqrt

class Title(private val text: String, private val size: Int) : IComponent {
    private var x = 0
    private var y = 0
    private var ratio = 0f

    override fun draw(page: Page, g: Graphics) {
        val oldFont = g.font
        g.font = Font(g.font.name, Font.BOLD, (size * ratio).toInt())
        g.drawString(text, x, y)
        g.font = oldFont
    }

    override fun resize(page: Page, dim: DimData) {
        val i = dim.width / 2 - text.length * 7f
        x = (if (sqrt(i) > 25) i * .9f else i).toInt()
        y = dim.height / 25 + dim.ratio.toInt() * 4
        ratio = dim.ratio.pow(4)
    }
}