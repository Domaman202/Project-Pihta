package ru.DmN.siberia

import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parser.utils.baseParseNode
import ru.DmN.siberia.ups.NUPExport
import ru.DmN.siberia.ups.NUPUse
import ru.DmN.siberia.ups.NUPUseCtx
import ru.DmN.pht.std.ups.NUPDefault
import ru.DmN.pht.std.utils.StdModule

object Siberia : StdModule("siberia") {
    init {
        adda("export",   NUPExport)
        addb("progn",    NUPDefault)
        addb("use-ctx",  NUPUseCtx)
        addb("use",      NUPUse)
    }

    override fun load(parser: Parser, ctx: ParsingContext) {
        parser.parseNode = { baseParseNode(it) }
    }
}