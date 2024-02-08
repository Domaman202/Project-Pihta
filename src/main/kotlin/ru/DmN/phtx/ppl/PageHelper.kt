package ru.DmN.phtx.ppl

import ru.DmN.pht.utils.addSNP
import ru.DmN.pht.utils.addSNU
import ru.DmN.phtx.ppl.node.NodeTypes.*
import ru.DmN.phtx.ppl.utils.addSEP
import ru.DmN.siberia.utils.Module

object PageHelper : Module("phtx/ppl/page/helper") {
    private fun initParsers() {
        // a
        addSNP(A_SIZED)
        addSNP(A_OFFSET)
        // c
        addSNP(C_PAIR)
        addSNP(C_TRIPLE)
        addSNP(C_FOURFOLD)
        // e
        addSNP(E_IMAGE)
        addSNP(E_TEXT)
        addSNP(E_TITLE)
    }

    private fun initUnparsers() {
        // a
        addSNU(A_SIZED)
        addSNU(A_OFFSET)
        // c
        addSNU(C_PAIR)
        addSNU(C_TRIPLE)
        addSNU(C_FOURFOLD)
        // e
        addSNU(E_IMAGE)
        addSNU(E_TEXT)
        addSNU(E_TITLE)
    }

    private fun initProcessors() {
        // a
        addSEP(A_SIZED, "ru.DmN.phtx.ppl.attribute.ASized")
        addSEP(A_OFFSET, "ru.DmN.phtx.ppl.attribute.AOffset")
        // c
        addSEP(C_PAIR, "ru.DmN.phtx.ppl.container.CPair")
        addSEP(C_TRIPLE, "ru.DmN.phtx.ppl.container.CTriple")
        addSEP(C_FOURFOLD, "ru.DmN.phtx.ppl.container.CFourfold")
        // e
        addSEP(E_IMAGE, "ru.DmN.phtx.ppl.element.EImage")
        addSEP(E_TEXT, "ru.DmN.phtx.ppl.element.EText")
        addSEP(E_TITLE, "ru.DmN.phtx.ppl.element.ETitle")
    }

    init {
        initParsers()
        initUnparsers()
        initProcessors()
    }
}