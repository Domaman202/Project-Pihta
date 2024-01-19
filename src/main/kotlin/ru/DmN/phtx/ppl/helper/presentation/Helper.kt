package ru.DmN.phtx.ppl.helper.presentation

import ru.DmN.pht.utils.addSNP
import ru.DmN.phtx.ppl.node.NodeTypes.*
import ru.DmN.siberia.utils.Module

object Helper : Module("phtx/ppl/presentation/helper") {
    override fun initParsers() {
        // l
        addSNP(LIST)
    }
}