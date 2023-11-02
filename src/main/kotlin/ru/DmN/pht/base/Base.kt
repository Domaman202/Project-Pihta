package ru.DmN.pht.base

import ru.DmN.pht.base.compiler.java.BaseJava
import ru.DmN.pht.base.parsers.NPNodesList
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.unparsers.NUDefault
import ru.DmN.pht.base.ups.NUPExport
import ru.DmN.pht.base.ups.NUPUseCtx
import ru.DmN.pht.std.utils.StdModule

object Base : StdModule("base") {
    init {
        add("export",   NUPExport)
        add("progn",    NPNodesList, NUDefault, NRDefault)
        add("use-ctx",  NUPUseCtx)

        ///

        BaseJava.init()
    }
}