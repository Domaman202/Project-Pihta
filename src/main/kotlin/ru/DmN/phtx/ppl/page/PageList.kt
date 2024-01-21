package ru.DmN.phtx.ppl.page

import ru.DmN.phtx.ppl.element.Element
import ru.DmN.phtx.ppl.element.Element.Offset
import ru.DmN.phtx.ppl.element.Element.Size
import ru.DmN.phtx.ppl.element.Element.SizeType.DYNAMIC
import ru.DmN.phtx.ppl.element.Element.SizeType.FIXED
import java.awt.Dimension
import java.awt.Graphics2D

class PageList(val list: MutableList<Element> = mutableListOf()) : Page() {
    operator fun plusAssign(element: Element) {
        list.add(element)
    }

    override fun paint(w: Dimension, g: Graphics2D) {
        super.paint(w, g)
        //
        var free = Size(w.width, w.height)
        val elements = list.map { Triple(it, it.type, it.size(w, g)) }
        //
        elements.filter { it.second == FIXED }.forEach { (_, _, size) ->
            free = free.sub(0, size.height)
        }
        //
        elements.filter { it.second == DYNAMIC }.drop(1).forEach { _ ->
            free = free.div()
        }
        //
        var offset = Offset.EMPTY
        elements.forEach { (element, type, size) ->
            element.paint(offset, free, w, g)
            offset = offset.offset(if (type == FIXED) size else free)
        }
    }
}