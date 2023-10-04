package ru.DmN.pht.std.collections

import ru.DmN.pht.std.base.ups.NUPDefault
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.collections.compiler.java.StdCollectionsJava
import ru.DmN.pht.std.collections.processors.NRArrayOf
import ru.DmN.pht.std.collections.ups.NUPAGet
import ru.DmN.pht.std.collections.ups.NUPASet
import ru.DmN.pht.std.collections.ups.NUPNewArray

object StdCollections : StdModule("std/collections") {
    init {
        add("aset",     NUPASet)
        add("aget",     NUPAGet)
        add("new-array",NUPNewArray)
        add("array-of", NUPDefault, NRArrayOf)

        StdCollectionsJava.init()
    }
}