package ru.DmN.siberia

import ru.DmN.pht.base.compiler.java.BaseJava
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.parser.utils.baseParseNode
import ru.DmN.pht.base.ups.NUPExport
import ru.DmN.pht.base.ups.NUPUse
import ru.DmN.pht.base.ups.NUPUseCtx
import ru.DmN.pht.std.ups.NUPDefault
import ru.DmN.pht.std.utils.StdModule

object Base : StdModule("base") {
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