package ru.DmN.phtx.spt

import ru.DmN.phtx.spt.components.IComponent
import ru.DmN.phtx.spt.internal.DrawCallbackJPanel
import java.awt.Graphics

class Page(val window: Window) {
    val jpanel = DrawCallbackJPanel(::draw)
    private val components: MutableList<IComponent> = ArrayList()
    private val dim: DimData = DimData(window.width, window.height).apply { resize(window.jframe.width, window.jframe.height) }

    fun add(component: IComponent) {
        components += component
    }

    fun show() {
        if (window.pageIndex > 0)
            window.jframe.remove(window.pages[window.pageIndex - 1].jpanel)
        jpanel.components.forEach(jpanel::remove)
        components.forEach { it.show(this) }
        window.jframe.add(jpanel)
    }

    fun draw(graphics: Graphics) {
        components.forEach { it.draw(this, graphics) }
    }

    fun resize(width: Int, height: Int) {
        dim.resize(width, height)
        components.forEach { it.resize(this, dim) }
    }
}