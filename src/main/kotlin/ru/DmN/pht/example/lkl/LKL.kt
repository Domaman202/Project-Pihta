package ru.DmN.pht.example.lkl

import ru.DmN.pht.std.ups.NUPDefault
import ru.DmN.pht.std.utils.StdModule

/// LOL KEK LANGUAGE
object LKL : StdModule("example/lkl") {
    init {
        add(Regex("[lk]+"), NUPDefault)
    }
}