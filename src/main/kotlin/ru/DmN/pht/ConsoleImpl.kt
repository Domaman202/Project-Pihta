package ru.DmN.pht

import ru.DmN.siberia.console.BaseCommands
import ru.DmN.siberia.console.BaseConsole
import ru.DmN.siberia.console.BuildCommands

object ConsoleImpl : BaseConsole() {
    @JvmStatic
    fun main(args: Array<String>) {
        commands += BaseCommands.HELP
        commands += BuildCommands.MODULE_SELECT
        commands += BuildCommands.MODULE_PRINT
        commands += BuildCommands.MODULE_COMPILE
        commands += BuildCommands.MODULE_RUN
        commands += BuildCommands.MODULE_RUN_TEST
        run(args)
    }
}