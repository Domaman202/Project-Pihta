package ru.DmN.phtx.ppl.utils

import ru.DmN.phtx.ppl.processors.NRSimpleElement
import ru.DmN.phtx.ppl.processors.NRSimplePage
import ru.DmN.siberia.node.INodeType
import ru.DmN.siberia.utils.Module

fun Module.addSPP(token: INodeType, type: String) {
    this.add(token, NRSimplePage(type))
}

fun Module.addSEP(token: INodeType, type: String) {
    this.add(token, NRSimpleElement(type))
}
