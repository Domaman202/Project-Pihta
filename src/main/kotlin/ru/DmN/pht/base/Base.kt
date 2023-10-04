package ru.DmN.pht.base

import ru.DmN.pht.base.compiler.java.BaseJava
import ru.DmN.pht.base.parser.parsers.NPNodesList
import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.unparser.unparsers.NUDefault
import ru.DmN.pht.base.ups.NUPTest
import ru.DmN.pht.base.ups.NUPUse
import ru.DmN.pht.base.ups.NUPUseCtx
import ru.DmN.pht.std.base.utils.StdModule

object Base : StdModule("base") {
    init {
        add("use-ctx",  NUPUseCtx)
        add("use",      NUPUse)
        add("progn",    NPNodesList,    NUDefault,    NRDefault)
        add("test",     NUPTest)

        BaseJava.init()
    }
}