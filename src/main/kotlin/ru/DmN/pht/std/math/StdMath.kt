package ru.DmN.pht.std.math

import ru.DmN.pht.std.base.ups.UPDefault
import ru.DmN.pht.std.base.utils.StdModule
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

object StdMath : StdModule("std/math") { // todo: kotlin operators // todo: remake to NPDefault structure
    init {
        add(    name = "<=>",   UPDefault,  NCObjectCompare)
        for (name in listOf("=", "!=", "<", "<=", ">=", ">"))
            add(name = name,    NPEquals,   NUEquals,   UPDefault,  NCPrimitiveCompare)
        for (name in listOf("+", "-", "*", "/"))
            add(name = name,    NPMathNA,   NUMathNA,   UPDefault,  NCMathNA)
        add(    name = "!",     NPNot,      NUNot,      UPDefault,  NCNot)
    }
}