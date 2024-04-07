package ru.DmN.pht.cpp.console

import ru.DmN.pht.cpp.console.commands.ModuleRun
import ru.DmN.pht.cpp.console.commands.ModuleRunTest

object CppCommands {
    @JvmStatic
    val ALL_COMMANDS = listOf(ModuleRun, ModuleRunTest)
}