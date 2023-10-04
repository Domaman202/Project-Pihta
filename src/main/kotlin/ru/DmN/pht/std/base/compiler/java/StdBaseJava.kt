package ru.DmN.pht.std.base.compiler.java

import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.utils.ModuleCompilers
import ru.DmN.pht.std.base.StdBase
import ru.DmN.pht.std.fp.compiler.java.compilers.NCBody
import ru.DmN.pht.std.fp.compiler.java.compilers.NCDef
import ru.DmN.pht.std.base.compiler.java.compilers.NCUnit

object StdBaseJava : ModuleCompilers(StdBase) {
    override fun onInitialize() {
        // Пространства имён
        add("ns",   NCDefault)
        add("sns",  NCDefault)
        // Тело
        add("body", NCBody)
        // Переменные
        add("def",  NCDef)
        // Пустой блок
        add("unit", NCUnit)
    }
}