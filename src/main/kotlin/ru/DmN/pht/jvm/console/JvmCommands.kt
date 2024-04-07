package ru.DmN.pht.jvm.console

import ru.DmN.pht.jvm.console.commands.ModuleRun
import ru.DmN.pht.jvm.console.commands.ModuleRunTest
import java.io.File
import java.net.URLClassLoader

object JvmCommands {
    @JvmStatic
    val ALL_COMMANDS = listOf(ModuleRun, ModuleRunTest)

    @JvmStatic
    fun getAppClass(): Class<*> =
        Class.forName("App", true, URLClassLoader(arrayOf(File("dump").toURI().toURL())))
}