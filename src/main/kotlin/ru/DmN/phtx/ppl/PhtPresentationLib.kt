package ru.DmN.phtx.ppl

import ru.DmN.pht.utils.addNP
import ru.DmN.pht.utils.addSNU
import ru.DmN.phtx.ppl.node.NodeTypes.PRESENTATION
import ru.DmN.phtx.ppl.parsers.NPPresentation
import ru.DmN.phtx.ppl.processors.NRPresentation
import ru.DmN.siberia.utils.Module

object PhtPresentationLib : Module("phtx/ppl") {
    override fun initParsers() {
        // p
        addNP("presentation", NPPresentation)
    }

    override fun initUnparsers() {
        // p
        addSNU(PRESENTATION)
    }

    override fun initProcessors() {
        // p
        add(PRESENTATION, NRPresentation)
    }
}