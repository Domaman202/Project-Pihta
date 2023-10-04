package ru.DmN.pht.std.collections.compiler.java

import ru.DmN.pht.base.utils.ModuleCompilers
import ru.DmN.pht.std.collections.StdCollections
import ru.DmN.pht.std.collections.compiler.java.compilers.NCAGet
import ru.DmN.pht.std.collections.compiler.java.compilers.NCASet
import ru.DmN.pht.std.collections.compiler.java.compilers.NCNewArray

object StdCollectionsJava : ModuleCompilers(StdCollections) {
    override fun onInitialize() {
        add("aset",     NCASet)
        add("aget",     NCAGet)
        add("new-array",NCNewArray)
    }
}