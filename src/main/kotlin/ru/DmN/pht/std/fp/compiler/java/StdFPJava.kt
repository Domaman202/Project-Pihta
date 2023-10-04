package ru.DmN.pht.std.fp.compiler.java

import ru.DmN.pht.base.utils.ModuleCompilers
import ru.DmN.pht.std.fp.StdFP
import ru.DmN.pht.std.fp.compiler.java.compilers.NCDefn
import ru.DmN.pht.std.fp.compiler.java.compilers.NCGetA

object StdFPJava : ModuleCompilers(StdFP) {
    override fun onInitialize() {
        // Функция
        add("defn", NCDefn)
        // Геттеры
        add("get",  NCGetA)
    }
}