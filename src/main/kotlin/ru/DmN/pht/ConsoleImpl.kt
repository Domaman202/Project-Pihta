package ru.DmN.pht

import ru.DmN.pht.console.CppCommands
import ru.DmN.pht.console.JvmCommands
import ru.DmN.pht.utils.Platforms
import ru.DmN.siberia.console.BaseCommands
import ru.DmN.siberia.console.BaseConsole
import ru.DmN.siberia.console.BuildCommands

object ConsoleImpl : BaseConsole() {
    @JvmStatic
    fun main(args: Array<String>) {
        run(args)
    }

    init {
        commands += BaseCommands.ALL_COMMANDS
        commands += BuildCommands.ALL_COMMANDS
        commands += JvmCommands.ALL_COMMANDS
        commands += CppCommands.ALL_COMMANDS

        Platforms
    }
}