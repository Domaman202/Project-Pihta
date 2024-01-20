package ru.DmN.phtx.ppl

import ru.DmN.pht.utils.addSNP
import ru.DmN.pht.utils.addSNU
import ru.DmN.phtx.ppl.node.NodeTypes.*
import ru.DmN.phtx.ppl.utils.addSEP
import ru.DmN.siberia.utils.Module

object PageHelper : Module("phtx/ppl/page/helper") {
    override fun initParsers() {
        // e
        addSNP(E_TEXT)
        addSNP(E_TITLE)
    }

    override fun initUnparsers() {
        // e
        addSNU(E_TEXT)
        addSNU(E_TITLE)
    }

    override fun initProcessors() {
        // e
        addSEP(E_TEXT, "ru.DmN.phtx.ppl.element.EText")
        addSEP(E_TITLE, "ru.DmN.phtx.ppl.element.ETitle")
    }
}