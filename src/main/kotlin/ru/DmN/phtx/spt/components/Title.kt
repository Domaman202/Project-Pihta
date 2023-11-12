package ru.DmN.phtx.spt.components

import ru.DmN.phtx.spt.DimData
import ru.DmN.phtx.spt.Page
import java.awt.Font
import javax.swing.JLabel
import kotlin.math.pow

class Title(text: String, val size: Int) : IComponent {
    private val jlabel = JLabel(text)

    override fun show(page: Page) {
        page.jpanel.add(jlabel)
    }

    override fun resize(page: Page, dim: DimData) {
        val size = (size * dim.ratio.pow((4 * dim.ratio) / (dim.ratio * 1.5f))).toInt()
        jlabel.font = Font(jlabel.font.name, jlabel.font.style, size)
        jlabel.setBounds(dim.width / 2 - jlabel.text.length * 14 / 2, 0, dim.width / 2, (26 * dim.heightRatio).toInt())
    }
}