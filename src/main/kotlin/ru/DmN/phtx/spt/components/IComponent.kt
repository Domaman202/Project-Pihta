package ru.DmN.phtx.spt.components

import ru.DmN.phtx.spt.utils.DimData
import ru.DmN.phtx.spt.Page
import java.awt.Graphics

interface IComponent {
    fun show(page: Page) = Unit
    fun draw(page: Page, g: Graphics) = Unit
    fun resize(page: Page, dim: DimData) = Unit
}