package ru.DmN.test

import ru.DmN.pht.jvm.console.JvmCommands
import ru.DmN.pht.utils.Platforms
import ru.DmN.siberia.console.BaseCommands
import ru.DmN.siberia.console.BaseConsole
import ru.DmN.siberia.console.BuildCommands
import ru.DmN.test.console.TestCommands

object ConsoleImpl : BaseConsole() {
    @JvmStatic
    fun main(args: Array<String>) {
        run(args)
    }

    init {
        commands += BaseCommands.ALL_COMMANDS
        commands += BuildCommands.ALL_COMMANDS
        commands += JvmCommands.ALL_COMMANDS
        commands += TestCommands.ALL_COMMANDS

        Platforms
    }
}

// -p jvm -m test -mc -mr
// -tup -tuu