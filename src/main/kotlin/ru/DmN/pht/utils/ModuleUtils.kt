package ru.DmN.pht.utils

import ru.DmN.pht.parsers.NPSA
import ru.DmN.siberia.node.INodeType
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.SimpleNP
import ru.DmN.siberia.utils.Module
import ru.DmN.siberia.utils.Module.Companion.toRegularExpr

fun Module.addNP(pattern: String, parser: INodeParser) {
    add(pattern.toRegularExpr(), parser)
}

fun Module.addSNP(type: INodeType) {
    add(type.operation.toRegularExpr(), SimpleNP(type))
}

fun Module.addSANP(type: INodeType) {
    add(type.operation.toRegularExpr(), NPSA(type))
}
