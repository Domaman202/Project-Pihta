package ru.DmN.pht.example.bf

import ru.DmN.pht.std.base.ups.NUPDefault
import ru.DmN.pht.std.base.utils.StdModule

/// BrainF#ck
object BF : StdModule("example/bf") {
    init {
        add("bf",   NUPDefault)
    }
}