package ru.DmN.pht.std.collections

import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.std.collections.compiler.java.compilers.NCList
import ru.DmN.pht.std.unparsers.NUDefault
import ru.DmN.pht.base.utils.Module

object StdCollections : Module("std/collections") {
    init {
        add(name = "array", parser = NPDefault, unparser = NUDefault)
        add(name = "list",  parser = NPDefault, unparser = NUDefault,   compiler = NCList)
        add(name = "map",   parser = NPDefault, unparser = NUDefault)
    }
}