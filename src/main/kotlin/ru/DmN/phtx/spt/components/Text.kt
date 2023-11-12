package ru.DmN.phtx.spt.components

import ru.DmN.phtx.spt.DimData
import ru.DmN.phtx.spt.Page
import java.awt.Font
import javax.swing.JLabel
import kotlin.math.pow

class Text(text: String, val x: Int, val y: Int, val width: Int, val height: Int, val size: Int) : IComponent {
    private val jlabel = JLabel(text)

    override fun show(page: Page) {
        page.jpanel.add(jlabel)
    }

    override fun resize(page: Page, dim: DimData) {
        val size = (size * dim.ratio.pow((2 * dim.ratio) / dim.ratio)).toInt()
        jlabel.font = Font(jlabel.font.name, jlabel.font.style, size)
        jlabel.setBounds(
            (x * dim.widthRatio).toInt(),
            (y * dim.heightRatio).toInt(),
            (width * dim.widthRatio).toInt(),
            (height * dim.heightRatio).toInt()
        )
    }
}