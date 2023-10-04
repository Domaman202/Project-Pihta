package ru.DmN.pht.std.oop

import ru.DmN.pht.std.base.processor.processors.NRImport
import ru.DmN.pht.std.base.ups.*
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.oop.compiler.java.StdOOPJava
import ru.DmN.pht.std.oop.parsers.NPMCallB
import ru.DmN.pht.std.oop.processors.*
import ru.DmN.pht.std.oop.ups.*

object StdOOP : StdModule("std/oop") {
    init {
        // Импорт типов
        add("import",           NUPDefault, NRImport)
        // Импорт расширений
        add("import-extends",   NUPDefault, NRImportExtends)
        // Аннотации
        add("@abstract",NUPDefault, NRAbstract)
        add("@final",   NUPDefault, NRFinal)
        add("@open",    NUPDefault, NROpen)
        add("@static",  NUPDefault, NRStatic)
        // Объект / Класс / Интерфейс
        add("obj",  NUPClass)
        add("cls",  NUPClass)
        add("inf",  NUPClass)
        // Расширение
        add("efn",  NUPEFn)
        // Конструктор
        add("ctor", NUPCtor)
        // Поле
        add("field",    NUPField)
        // Вызов
        add("mcall",    NUPMCallA)
        add("mcall!",   NPMCallB, null, null)
        // Сеттеры
        add("fset",     NUPFSetA)
        add("fset!",    null, NRFSetB)
        // Геттеры
        add("fget",     NUPFGetA)
        add("fget!",    null, NRFGetB)

        StdOOPJava.init()
    }
}