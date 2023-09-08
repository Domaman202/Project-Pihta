package ru.DmN.pht.std.math

import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.unparsers.NUDefault
import ru.DmN.pht.std.math.compiler.java.compilers.NCMathNA
import ru.DmN.pht.std.math.compiler.java.compilers.NCNot
import ru.DmN.pht.std.math.compiler.java.compilers.NCObjectCompare
import ru.DmN.pht.std.math.compiler.java.compilers.NCPrimitiveCompare
import ru.DmN.pht.std.math.parsers.NPEquals
import ru.DmN.pht.std.math.parsers.NPMathNA
import ru.DmN.pht.std.math.parsers.NPNot
import ru.DmN.pht.std.math.unparsers.NUEquals
import ru.DmN.pht.std.math.unparsers.NUMathNA
import ru.DmN.pht.std.math.unparsers.NUNot

object StdMath : Module("std/math") { // todo: kotlin operators // todo: remake to NPDefault structure
    init {
        add(    name = "<=>",   NPDefault,  NUDefault,  NRDefault,  NCObjectCompare)
        for (name in listOf("=", "!=", "<", "<=", ">=", ">"))
            add(name = name,    NPEquals,   NUEquals,   NRDefault,  NCPrimitiveCompare)
        for (name in listOf("+", "-", "*", "/"))
            add(name = name,    NPMathNA,   NUMathNA,   NRDefault,  NCMathNA)
        add(    name = "!",     NPNot,      NUNot,      NRDefault,  NCNot)
    }
}