package ru.DmN.phtx.ppl

import ru.DmN.pht.utils.addNP
import ru.DmN.pht.utils.addSNP
import ru.DmN.pht.utils.addSNU
import ru.DmN.phtx.ppl.node.NodeTypes.*
import ru.DmN.phtx.ppl.parsers.NPPresentation
import ru.DmN.phtx.ppl.processors.NRIncImg
import ru.DmN.phtx.ppl.processors.NRIncTxt
import ru.DmN.phtx.ppl.processors.NRPresentation
import ru.DmN.siberia.utils.Module

object PhtPresentationLib : Module("phtx/ppl") {
    private fun initParsers() {
        // i
        addSNP(INC_IMG)
        addSNP(INC_TXT)
        // p
        addNP("presentation", NPPresentation)
    }

    private fun initUnparsers() {
        // i
        addSNU(INC_IMG)
        addSNU(INC_TXT)
        // p
        addSNU(PRESENTATION)
    }

    private fun initProcessors() {
        // i
        add(INC_IMG,        NRIncImg)
        add(INC_TXT,        NRIncTxt)
        // p
        add(PRESENTATION,   NRPresentation)
    }

    init {
        initParsers()
        initUnparsers()
        initProcessors()
    }
}