package ru.DmN.test.console.commands

import ru.DmN.siberia.console.Console
import ru.DmN.siberia.console.utils.Command
import ru.DmN.test.console.TestCommands.forEachTests
import java.io.File

object TestClear : Command(
    "test-clear",
    "tc",
    "Тест",
    "Очистка тестовых данных",
    "Удаляет все данные тестов у всех тестов.",
    emptyList()
) {
    override fun available(console: Console): Boolean =
        true

    override fun builderAvailable(flags: Map<Any?, Any?>): Boolean =
        true

    override fun action(console: Console, vararg args: Any?) {
        println("Начало очистки всех тестовых данных.")
        forEachTests { dir, _ ->
            println(dir)
            File(dir, "test").deleteRecursively()
        }
        println("Очистка всех тестовых данных завершено.")
    }
}