package ru.DmN.pht.std.value.compiler.java

import ru.DmN.pht.base.utils.ModuleCompilers
import ru.DmN.pht.std.fp.compiler.java.compilers.NCGetB
import ru.DmN.pht.std.value.StdValue
import ru.DmN.pht.std.value.compiler.java.compilers.NCValue

object StdValueJava : ModuleCompilers(StdValue) {
    override fun onInitialize() {
        // Имя или get
        add("get-or-name!", NCGetB)
        // Значение
        add("value",        NCValue)
    }
}