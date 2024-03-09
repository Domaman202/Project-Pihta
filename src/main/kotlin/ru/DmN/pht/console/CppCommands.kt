package ru.DmN.pht.console

import ru.DmN.pht.utils.Platforms.CPP
import ru.DmN.siberia.console.Console
import ru.DmN.siberia.console.ctx.isModule
import ru.DmN.siberia.console.utils.Argument
import ru.DmN.siberia.console.utils.ArgumentType
import ru.DmN.siberia.console.utils.Command
import ru.DmN.siberia.processor.utils.platform
import java.lang.ProcessBuilder.Redirect.INHERIT

object CppCommands {
    val MODULE_RUN = Command(
        "module-run",
        "mr",
        "Модуль",
        "Запуск модуля",
        "Запускает модуль.",
        emptyList(),
        CppCommands::onCppModuleAvailable,
        CppCommands::moduleRun
    )

    val MODULE_RUN_TEST = Command(
        "module-run-test",
        "mrt",
        "Модуль",
        "Запуск модуля",
        "Запускает модуль.",
        listOf(
            Argument(
                "index",
                "Номер",
                ArgumentType.INT,
                "Номер теста модуля.",
                "Введите номер теста"
            )
        ),
        CppCommands::onCppModuleAvailable,
        CppCommands::moduleRunTest
    )

    val ALL_COMMANDS = listOf(MODULE_RUN, MODULE_RUN_TEST)

    @JvmStatic
    fun moduleRunTest(console: Console, vararg args: Any?) {
        val index = args[0] as Int
        //
        console.println("Запуск...")
        try {
            ProcessBuilder("dump/main", "$index").redirectOutput(INHERIT).redirectErrorStream(true).start().waitFor()
            console.println("Запуск окончен успешно!")
        } catch (t: Throwable) {
            console.println("Запуск окончен с ошибками:")
            t.printStackTrace(console.print)
        }
    }

    @JvmStatic
    fun moduleRun(console: Console, vararg args: Any?) {
        console.println("Запуск...")
        try {
            ProcessBuilder("dump/main").redirectOutput(INHERIT).redirectErrorStream(true).start().waitFor()
            console.println("Запуск окончен успешно!")
        } catch (t: Throwable) {
            console.println("Запуск окончен с ошибками:")
            t.printStackTrace(console.print)
        }
    }

    @JvmStatic
    fun onCppModuleAvailable(console: Console): Boolean =
        console.isModule && console.platform == CPP
}