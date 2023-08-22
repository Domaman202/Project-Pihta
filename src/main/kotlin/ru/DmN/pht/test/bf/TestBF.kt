package ru.DmN.pht.test.bf

import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.unparsers.NUDefault
import ru.DmN.pht.test.bf.compiler.java.compilers.*
import ru.DmN.pht.test.bf.parsers.NPSimple
import ru.DmN.pht.test.bf.unparsers.NUSimple

object TestBF : Module("test/bf") {
    init {
        add("bf",   NPDefault,  NUDefault,  NCBF)
        add("next", NPSimple,   NUSimple,   NCNext)
        add("prev", NPSimple,   NUSimple,   NCPrev)
        add("inc",  NPSimple,   NUSimple,   NCInc)
        add("dec",  NPSimple,   NUSimple,   NCDec)
        add("put",  NPSimple,   NUSimple,   NCPut)
        add("start",NPSimple,   NUSimple,   NCStart)
        add("stop", NPSimple,   NUSimple,   NCStop)
    }
}