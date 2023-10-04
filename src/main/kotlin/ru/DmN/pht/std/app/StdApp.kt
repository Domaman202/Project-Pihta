package ru.DmN.pht.std.app

import ru.DmN.pht.std.app.processors.NRApp
import ru.DmN.pht.std.app.processors.NRAppFn
import ru.DmN.pht.std.base.ups.NUPDefault
import ru.DmN.pht.std.base.utils.StdModule

object StdApp : StdModule("std/app") {
    init {
        add("app",      NUPDefault, NRApp)
        add("app-fn",   NUPDefault, NRAppFn)
    }
}