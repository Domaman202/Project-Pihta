package ru.DmN.phtx.ppl.helper.page

import ru.DmN.pht.utils.addSNP
import ru.DmN.phtx.ppl.node.NodeTypes.*
import ru.DmN.siberia.utils.Module

object Helper : Module("phtx/ppl/page/helper") {
    override fun initParsers() {
        // t
        addSNP(TITLE)
    }
}