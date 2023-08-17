package ru.DmN.pht.std.math

import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.std.math.compiler.java.compilers.NCPrimitiveCompare
import ru.DmN.pht.std.math.compiler.java.compilers.NCMathNA
import ru.DmN.pht.std.math.compiler.java.compilers.NCNot
import ru.DmN.pht.std.math.compiler.java.compilers.NCObjectCompare
import ru.DmN.pht.std.math.parsers.NPEquals
import ru.DmN.pht.std.math.parsers.NPMathNA
import ru.DmN.pht.std.math.parsers.NPNot
import ru.DmN.pht.std.math.unparsers.NUEquals
import ru.DmN.pht.std.math.unparsers.NUMathNA
import ru.DmN.pht.std.math.unparsers.NUNot
import ru.DmN.pht.std.unparsers.NUDefault
import ru.DmN.pht.std.utils.Module

object StdMath : Module("std/math") { // todo: kotlin operators
    init {
        add(        name = "<=>",   parser = NPDefault, unparser = NUDefault,   compiler = NCObjectCompare)
        for (name in listOf("=", "!=", "<", "<=", ">=", ">"))
            add(    name = name,    parser = NPEquals,  unparser = NUEquals,    compiler = NCPrimitiveCompare)
        for (name in listOf("+", "-", "*", "/"))
            add(    name = name,    parser = NPMathNA,  unparser = NUMathNA,    compiler = NCMathNA)
        add(        name = "!",     parser = NPNot,     unparser = NUNot,       compiler = NCNot)
    }
}