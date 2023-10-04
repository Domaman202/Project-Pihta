package ru.DmN.pht.std.oop.compiler.java

import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.utils.ModuleCompilers
import ru.DmN.pht.std.oop.StdOOP
import ru.DmN.pht.std.oop.compiler.java.compilers.NCClass
import ru.DmN.pht.std.oop.compiler.java.compilers.NCCtor
import ru.DmN.pht.std.oop.compiler.java.compilers.NCField
import ru.DmN.pht.std.oop.compiler.java.compilers.NCMCall

object StdOOPJava : ModuleCompilers(StdOOP) {
    override fun onInitialize() {
        // Аннотации
        add("@abstract",NCDefault)
        add("@final",   NCDefault)
        add("@open",    NCDefault)
        add("@static",  NCDefault)
        // Объект / Класс / Интерфейс
        add("obj",      NCClass)
        add("cls",      NCClass)
        add("itf",      NCClass)
        // Конструктор
        add("ctor",     NCCtor)
        // Поле
        add("field",    NCField)
        // Вызов
        add("mcall",    NCMCall)
    }
}