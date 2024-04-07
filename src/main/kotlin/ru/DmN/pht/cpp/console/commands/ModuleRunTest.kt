package ru.DmN.pht.cpp.console.commands

import ru.DmN.pht.utils.Platforms.CPP
import ru.DmN.siberia.console.Console
import ru.DmN.siberia.console.ctx.isModule
import ru.DmN.siberia.console.utils.Argument
import ru.DmN.siberia.console.utils.ArgumentType
import ru.DmN.siberia.console.utils.Command
import ru.DmN.siberia.processor.utils.platform
import java.lang.ProcessBuilder.Redirect.INHERIT

object ModuleRunTest : Command(
    "module-run-test",
    "mrt",
    "[C++] Модуль",
    "Запуск теста модуля",
    "Запускает модуль.",
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
        console.isModule && console.platform == CPP

    override fun builderAvailable(flags: Map<Any?, Any?>): Boolean =
        flags["module"] != null && flags["platform"] == "cpp"

    override fun action(console: Console, vararg args: Any?) {
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
}