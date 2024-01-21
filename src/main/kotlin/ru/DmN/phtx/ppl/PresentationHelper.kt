package ru.DmN.phtx.ppl

import ru.DmN.pht.utils.addNP
import ru.DmN.pht.utils.addSNU
import ru.DmN.phtx.ppl.node.NodeTypes.PAGE_LIST
import ru.DmN.phtx.ppl.parsers.NPPageList
import ru.DmN.phtx.ppl.processors.NRPageList
import ru.DmN.siberia.utils.Module

object PresentationHelper : Module("phtx/ppl/presentation/helper") {
    override fun initParsers() {
        // p
        addNP("page-list", NPPageList)
    }

    override fun initUnparsers() {
        // p
        addSNU(PAGE_LIST)
    }

    override fun initProcessors() {
        // p
        add(PAGE_LIST, NRPageList)
    }
}