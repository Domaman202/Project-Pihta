package ru.DmN.pht.jvm.console.commands

import ru.DmN.pht.jvm.console.JvmCommands.getAppClass
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.siberia.console.Console
import ru.DmN.siberia.console.ctx.isModule
import ru.DmN.siberia.console.utils.Command
import ru.DmN.siberia.processor.utils.platform

object ModuleRun : Command(
    "module-run",
    "mr",
    "[JVM] Модуль",
    "Запуск модуля",
    "Запускает модуль.",
    emptyList()
) {
    override fun available(console: Console): Boolean =
        console.isModule && console.platform == JVM

    override fun builderAvailable(flags: Map<Any?, Any?>): Boolean =
        flags["module"] != null && flags["platform"] == "jvm"

    override fun action(console: Console, vararg args: Any?) {
        console.println("Запуск...")
        try {
            console.println(getAppClass().getMethod("main").invoke(null))
            console.println("Запуск окончен успешно!")
        } catch (t: Throwable) {
            console.println("Запуск окончен с ошибками:")
            t.printStackTrace(console.print)
        }
    }
}