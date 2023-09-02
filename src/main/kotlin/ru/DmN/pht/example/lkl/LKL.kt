package ru.DmN.pht.example.lkl

import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.example.lkl.compiler.java.compilers.NCLKL
import ru.DmN.pht.std.base.unparsers.NUDefault

/// LOL KEK LANGUAGE
object LKL : Module("example/lkl") {
    init {
        add(Regex("[lk]+"), NPDefault, NUDefault, NCLKL)
    }
}