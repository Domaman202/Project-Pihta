package ru.DmN.pht.std

import ru.DmN.siberia.Console
import ru.DmN.siberia.ConsoleOld.initCompileAndRunModule
import ru.DmN.siberia.ConsoleOld.initCompileModule
import ru.DmN.siberia.ConsoleOld.initHelp
import ru.DmN.siberia.ConsoleOld.initModuleInfo

object Console : Console() {
    @JvmStatic
    fun Console.initProgramInfo() {
        this.actions.add(Triple("О программе", "Выводит информацию о программе.", Runnable {
            println("""
                Проект: Пихта
                Версия: 1.3.1
                Авторы: DomamaN202, Wannebetheshy
            """.trimIndent())
        }))
    }

    @JvmStatic
    fun main(args: Array<String>) {
        initHelp()
        initProgramInfo()
        initModuleInfo()
        initCompileModule()
        initCompileAndRunModule()
        run()
    }
}