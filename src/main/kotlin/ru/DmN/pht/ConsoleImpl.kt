package ru.DmN.pht

import ru.DmN.pht.console.JvmCommands
import ru.DmN.siberia.console.BaseCommands
import ru.DmN.siberia.console.BaseConsole
import ru.DmN.siberia.console.BuildCommands

object ConsoleImpl : BaseConsole() {
    @JvmStatic
    fun main(args: Array<String>) {
        commands += BaseCommands.HELP
        commands += BaseCommands.CMD_BUILDER
        commands += BuildCommands.MODULE_SELECT
        commands += BuildCommands.PLATFORM_SELECT
        commands += BuildCommands.MODULE_UNPARSE
        commands += BuildCommands.MODULE_PRINT
        commands += BuildCommands.MODULE_COMPILE
        commands += JvmCommands.MODULE_RUN
        commands += JvmCommands.MODULE_RUN_TEST
        run(args)
    }
}