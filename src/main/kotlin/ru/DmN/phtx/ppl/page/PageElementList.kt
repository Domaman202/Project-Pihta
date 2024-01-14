package ru.DmN.phtx.ppl.page

import ru.DmN.phtx.ppl.element.Element
import ru.DmN.phtx.ppl.element.Element.DrawDirection.DOWN_TO_UP
import ru.DmN.phtx.ppl.element.Element.DrawDirection.UP_TO_DOWN
import ru.DmN.phtx.ppl.element.Element.Offset
import ru.DmN.phtx.ppl.element.Element.Offset.Companion.EMPTY
import java.awt.Dimension
import java.awt.Graphics2D

class PageElementList(val list: List<Element>) : Page() {
    override fun paint(size: Dimension, g: Graphics2D) {
        super.paint(size, g)
        //
        var pair = Pair(EMPTY, list)
        pair = draw0(pair, size, g)
        pair = draw1(pair, size, g)
        pair = draw2(pair, size, g)
    }

    private fun draw0(pair: Pair<Offset, List<Element>>, size: Dimension, g: Graphics2D): Pair<Offset, List<Element>> {
        var offset = pair.first
        val painting = pair.second
        for ((i, it) in painting.withIndex()) {
            if (it.size == Element.SizeType.FIXED)
                offset = it.paint(UP_TO_DOWN, offset, size, g)
            else return Pair(offset, painting.drop(i).reversed())
        }
        return Pair(offset, emptyList())
    }

    private fun draw1(pair: Pair<Offset, List<Element>>, size: Dimension, g: Graphics2D): Pair<Offset, List<Element>> {
        var offset = pair.first
        val painting = pair.second
        for ((i, it) in painting.withIndex()) {
            if (it.size == Element.SizeType.FIXED)
                offset = it.paint(DOWN_TO_UP, offset, size, g)
            else return Pair(offset, painting.drop(i).reversed())
        }
        return Pair(offset, emptyList())
    }

    private fun draw2(pair: Pair<Offset, List<Element>>, size: Dimension, g: Graphics2D): Pair<Offset, List<Element>> {
        var offset = pair.first
        var painting = pair.second
        val list = ArrayList<Element>()
        for ((i, it) in painting.withIndex()) {
            if (it.size == Element.SizeType.DYNAMIC)
                list += it
            else {
                painting = painting.drop(i)
                break
            }
        }
        for (it in painting) {
            if (it.size == Element.SizeType.FIXED)
                offset = it.paint(DOWN_TO_UP, offset, size, g)
            else {
                when (list.size) {
                    0 -> break
                    1 -> it.paint(UP_TO_DOWN, offset, size, g)
                    2 -> {
                        val off = offset.right(size.width / 2)
                        list[0].paint(UP_TO_DOWN, off, size, g)
                        list[1].paint(UP_TO_DOWN, off.left(size.width / 2), size, g)
                    }

                    else -> throw RuntimeException("PPL не поддерживает более 2 динамических элементов на 1 странице!")
                }
            }
        }
        return Pair(offset, emptyList())
    }
}