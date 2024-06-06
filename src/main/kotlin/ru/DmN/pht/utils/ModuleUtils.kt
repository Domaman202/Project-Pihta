package ru.DmN.pht.utils

import ru.DmN.pht.module.utils.Module
import ru.DmN.pht.module.utils.Module.Companion.toRegularExpr
import ru.DmN.pht.parsers.SimpleMetaNP
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.SimpleNP
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.node.INodeType

fun Module.addNP(pattern: String, parser: INodeParser) {
    add(pattern.toRegularExpr(), parser)
}

fun Module.addSNP(type: INodeType) {
    add(type.operation.toRegularExpr(), SimpleNP(type))
}

fun Module.addSMNP(type: INodeType) {
    add(type.operation.toRegularExpr(), SimpleMetaNP(type))
}

fun Module.addSNU(type: INodeType) {
    add(type, NUDefault)
}