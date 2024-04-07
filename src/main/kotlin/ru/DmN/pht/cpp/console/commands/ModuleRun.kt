package ru.DmN.pht.cpp.console.commands

import ru.DmN.pht.utils.Platforms.CPP
import ru.DmN.siberia.console.Console
import ru.DmN.siberia.console.ctx.isModule
import ru.DmN.siberia.console.utils.Command
import ru.DmN.siberia.processor.utils.platform
import java.lang.ProcessBuilder.Redirect.INHERIT

object ModuleRun : Command(
    "module-run",
    "mr",
    "[C++] Модуль",
    "Запуск модуля",
    "Запускает модуль.",
    emptyList()
) {
    override fun available(console: Console): Boolean =
        console.isModule && console.platform == CPP

    override fun builderAvailable(flags: Map<Any?, Any?>): Boolean =
        flags["module"] != null && flags["platform"] == "cpp"

    override fun action(console: Console, vararg args: Any?) {
        console.println("Запуск...")
        try {
            ProcessBuilder("dump/main").redirectOutput(INHERIT).redirectErrorStream(true).start().waitFor()
            console.println("Запуск окончен успешно!")
        } catch (t: Throwable) {
            console.println("Запуск окончен с ошибками:")
            t.printStackTrace(console.print)
        }
    }
}