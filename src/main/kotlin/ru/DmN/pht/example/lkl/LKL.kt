package ru.DmN.pht.example.lkl

import ru.DmN.pht.std.base.ups.NUPDefault
import ru.DmN.pht.std.base.utils.StdModule

/// LOL KEK LANGUAGE
object LKL : StdModule("example/lkl") {
    init {
        add(Regex("[lk]+"), NUPDefault)
    }
}