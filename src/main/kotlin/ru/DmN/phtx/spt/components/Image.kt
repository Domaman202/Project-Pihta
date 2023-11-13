package ru.DmN.phtx.spt.components

import ru.DmN.phtx.spt.utils.DimData
import ru.DmN.phtx.spt.Page
import java.awt.Graphics
import java.awt.Image

class Image(private var image: Image, private val ix: Int, private val iy: Int, private val iwidth: Int, private val iheight: Int) : IComponent {
    private var x = ix
    private var y = iy

    init {
        image = image.getScaledInstance(iwidth, iheight, Image.SCALE_AREA_AVERAGING)
    }

    override fun draw(page: Page, g: Graphics) {
        g.drawImage(image, x, y, page.jpanel)
    }

    override fun resize(page: Page, dim: DimData) {
        x = (ix * dim.widthRatio).toInt()
        y = (iy * dim.heightRatio).toInt()
        image = image.getScaledInstance(
            (iwidth * dim.widthRatio).toInt(),
            (iheight * dim.heightRatio).toInt(),
            Image.SCALE_AREA_AVERAGING
        )
    }
}