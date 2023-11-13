package ru.DmN.phtx.spt

import ru.DmN.pht.std.ups.NUPDefault
import ru.DmN.pht.std.utils.StdModule
import ru.DmN.phtx.spt.components.Image
import ru.DmN.phtx.spt.components.Text
import ru.DmN.phtx.spt.components.Title
import ru.DmN.phtx.spt.processors.NRComponent
import ru.DmN.phtx.spt.processors.NRImgIFile
import ru.DmN.phtx.spt.processors.NRPage
import ru.DmN.phtx.spt.processors.NRWindow

object SPT : StdModule("phtx/spt") {
    init {
        // i
        add("image",        NUPDefault, NRComponent(Image::class.java))
        add("img-ifile",    NUPDefault, NRImgIFile)
        // p
        add("page",     NUPDefault, NRPage)
        // t
        add("text",     NUPDefault, NRComponent(Text::class.java))
        add("title",    NUPDefault, NRComponent(Title::class.java))
        // w
        add("window",   NUPDefault, NRWindow)
    }
}