package ru.DmN.pht.std.math

import ru.DmN.pht.std.math.compiler.java.compilers.NCCompare
import ru.DmN.pht.std.math.compiler.java.compilers.NCMathNA
import ru.DmN.pht.std.math.compiler.java.compilers.NCNot
import ru.DmN.pht.std.math.parsers.NPEquals
import ru.DmN.pht.std.math.parsers.NPMathNA
import ru.DmN.pht.std.math.parsers.NPNot
import ru.DmN.pht.std.math.unparsers.NUEquals
import ru.DmN.pht.std.math.unparsers.NUMathNA
import ru.DmN.pht.std.math.unparsers.NUNot
import ru.DmN.pht.std.utils.Module

object StdMath : Module("std/math") { // todo: kotlin operators
    init {
        for (name in listOf("=", "!=", "<", "<=", ">=", ">"))
            add(    name = name,    parser = NPEquals,  unparser = NUEquals,    compiler = NCCompare)
        for (name in listOf("+", "-", "*", "/"))
            add(    name = name,    parser = NPMathNA,  unparser = NUMathNA,    compiler = NCMathNA)
        add(        name = "!",     parser = NPNot,     unparser = NUNot,       compiler = NCNot)
    }
}