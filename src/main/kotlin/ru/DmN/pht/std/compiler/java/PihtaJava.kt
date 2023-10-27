package ru.DmN.pht.std.compiler.java

import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.utils.ModuleCompilers
import ru.DmN.pht.std.Pihta
import ru.DmN.pht.std.compilers.*

object PihtaJava : ModuleCompilers(Pihta) {
    override fun onInitialize() {
        /// BASE

        // Пространства имён
        add("ns",   NCDefault)
        add("sns",  NCDefault)
        // Пустой блок
        add("unit", NCUnit)

        /// COLLECTIONS

        // Работа с массивом
        add("array-size",   NCArraySize)
        add("!aset",        NCASet)
        add("!aget",        NCAGet)
        add("!new-array",   NCNewArray)

        /// ENUMS

        add("efield",   NCEField)

        /// FP

        // Функция
        add("defn",     NCDefn)
        // Лямбда
        add("!fn",      NCFn)
        // Тело
        add("body",     NCBody)
        // Цикл
        add("!cycle",   NCCycle)
        // Условие
        add("if",       NCIf)
        // Переменные
        add("!def",     NCDef)
        // Работа с типами
        add("!as",      NCAs)
        // Возврат
        add("ret",      NCRet)
        // Сеттеры
        add("set!",     NCSet)
        // Геттеры
        add("get",      NCGetA)

        /// MATH

        for (name in listOf("=", "!=", "<", "<=", ">=", ">"))
            add("!$name",   NCCompare)
        for (name in listOf("+", "-", "*", "/", "%"))
            add("!$name",   NCMath)
        for (name in listOf("++", "--"))
            add("!$name",   NCIncDec)
        add("!!",           NCNot)

        /// OOP

        // Аннотации
        add("@abstract",NCDefault)
        add("@final",   NCDefault)
        add("@open",    NCDefault)
        add("@static",  NCDefault)
        // Объект / Класс / Интерфейс
        for (name in listOf("obj", "cls", "itf"))
            add("!$name", NCClass)
        // Расширение
        add("efn",      NCDefn)
        // Конструктор
        add("ctor",     NCDefn)
        // Поле
        add("field",    NCField)
        // Создание объекта
        add("new",      NCNew)
        // Вызов
        add("mcall",    NCMCall)
        // Сеттеры
        add("fset",     NCFSet)
        // Геттеры
        add("!fget",    NCFGet)

        /// VALUE

        // Имя или get
        add("get-or-name!", NCGetB)
        // Значение
        add("value",        NCValue)
    }
}