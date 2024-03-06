package ru.DmN.pht.console

import ru.DmN.siberia.console.BuildCommands
import ru.DmN.siberia.console.Console
import ru.DmN.siberia.console.utils.Argument
import ru.DmN.siberia.console.utils.ArgumentType
import ru.DmN.siberia.console.utils.Command
import java.io.File
import java.net.URLClassLoader

object JvmCommands {
    val MODULE_RUN = Command(
        "module-run",
        "mr",
        "Модуль",
        "Запуск модуля",
        "Запускает модуль.",
        emptyList(),
        BuildCommands::moduleCtxAvailable,
        JvmCommands::moduleRun
    )

    val MODULE_RUN_TEST = Command(
        "module-run-test",
        "mrt",
        "Модуль",
        "Запуск теста модуля",
        "Запускает тест модуля.",
        listOf(
            Argument(
                "index",
                "Номер",
                ArgumentType.INT,
                "Номер теста модуля.",
                "Введите номер теста"
            )
        ),
        BuildCommands::moduleCtxAvailable,
        JvmCommands::moduleRunTest
    )

    val ALL_COMMANDS = listOf(MODULE_RUN, MODULE_RUN_TEST)

    @JvmStatic
    fun moduleRunTest(console: Console, vararg  args: Any?) {
        val index = args[0] as Int
        //
        console.println("Запуск...")
        try {
            console.println(Class.forName("Test$index", true, URLClassLoader(arrayOf(File("dump").toURI().toURL()))).getMethod("test").invoke(null))
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
            console.println(getAppClass().getMethod("main").invoke(null))
            console.println("Запуск окончен успешно!")
        } catch (t: Throwable) {
            console.println("Запуск окончен с ошибками:")
            t.printStackTrace(console.print)
        }
    }

    @JvmStatic
    fun getAppClass() =
        Class.forName("App", true, URLClassLoader(arrayOf(File("dump").toURI().toURL())))
}