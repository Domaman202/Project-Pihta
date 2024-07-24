package ru.DmN.pht.jvm.console.commands

import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.siberia.console.Console
import ru.DmN.siberia.console.ctx.isModule
import ru.DmN.siberia.console.ctx.module
import ru.DmN.siberia.console.utils.Argument
import ru.DmN.siberia.console.utils.ArgumentType
import ru.DmN.siberia.console.utils.Command
import ru.DmN.siberia.processor.utils.platform
import java.io.File
import java.net.URLClassLoader

object ModuleRunTest : Command(
    "module-run-test",
    "mrt",
    "[JVM] Модуль",
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
    )
) {
    override fun available(console: Console): Boolean =
        console.isModule && console.platform == JVM

    override fun builderAvailable(flags: Map<Any?, Any?>): Boolean =
        flags["module"] != null && flags["platform"] == "jvm"

    override fun action(console: Console, vararg args: Any?) {
        val index = args[0] as Int
        //
        console.println("Запуск...")
        try {
            console.println(Class.forName("Test$index", true, URLClassLoader(arrayOf(File("dump/${console.module.name}").toURI().toURL()))).getMethod("test").invoke(null))
            console.println("Запуск окончен успешно!")
        } catch (t: Throwable) {
            console.println("Запуск окончен с ошибками:")
            t.printStackTrace(console.print)
        }
    }
}