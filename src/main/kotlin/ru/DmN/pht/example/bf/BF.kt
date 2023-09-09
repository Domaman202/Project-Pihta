package ru.DmN.pht.example.bf

import ru.DmN.pht.example.bf.compiler.java.compilers.NCBF
import ru.DmN.pht.std.base.ups.UPDefault
import ru.DmN.pht.std.base.utils.StdModule

/// BrainF#ck
object BF : StdModule("example/bf") {
    init {
        add("bf",   UPDefault,  NCBF)
    }
}