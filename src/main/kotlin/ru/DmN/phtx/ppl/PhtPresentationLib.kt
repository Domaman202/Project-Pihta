package ru.DmN.phtx.ppl

import ru.DmN.pht.utils.addSNP
import ru.DmN.phtx.ppl.node.NodeTypes.*
import ru.DmN.siberia.utils.Module

object PhtPresentationLib : Module("phtx/ppl") {
    override fun initParsers() {
        // p
        addSNP(PRESENTATION)
    }
}