package ru.DmN.pht.std.collections

import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.std.collections.compiler.java.compilers.NCList
import ru.DmN.pht.std.unparsers.NUDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.collections.compiler.java.compilers.NCArrayOf
import ru.DmN.pht.std.collections.compiler.java.compilers.NCNewArray

object StdCollections : Module("std/collections") {
    init {
        add(name = "new-array", NPDefault,  NUDefault,  NCNewArray)
        add(name = "array-of",  NPDefault,  NUDefault,  NCArrayOf)
        add(name = "list",      NPDefault,  NUDefault,  NCList)
        add(name = "map",       NPDefault,  NUDefault)
    }
}