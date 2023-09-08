package ru.DmN.pht.example.bf

import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.example.bf.compiler.java.compilers.NCBF
import ru.DmN.pht.std.base.unparsers.NUDefault

/// BrainF#ck
object BF : Module("example/bf") {
    init {
        add("bf",   NPDefault,  NUDefault,  NRDefault,  NCBF)
    }
}