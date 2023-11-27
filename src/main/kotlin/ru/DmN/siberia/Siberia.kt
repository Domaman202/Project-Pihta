package ru.DmN.siberia

import ru.DmN.siberia.compiler.java.BaseJava
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parser.utils.baseParseNode
import ru.DmN.siberia.ups.NUPExport
import ru.DmN.siberia.ups.NUPUse
import ru.DmN.siberia.ups.NUPUseCtx
import ru.DmN.pht.std.ups.NUPDefault
import ru.DmN.pht.std.utils.StdModule

object Siberia : StdModule("siberia") {
    init {
        add("export",   NUPExport)
        add("progn",    NUPDefault)
        add("use-ctx",  NUPUseCtx)
        add("use",      NUPUse)

        ///

        BaseJava.init()
    }

    override fun load(parser: ru.DmN.siberia.Parser, ctx: ParsingContext) {
        parser.parseNode = { baseParseNode(it) }
    }
}