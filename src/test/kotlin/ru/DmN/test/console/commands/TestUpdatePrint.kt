package ru.DmN.test.console.commands

import ru.DmN.siberia.console.Console
import ru.DmN.siberia.console.utils.Command
import ru.DmN.test.console.TestCommands.forEachTests
import java.io.File

object TestUpdatePrint : Command(
    "test-update-print",
    "tup",
    "Тест",
    "Обновление 'печати'",
    "Обновляет данные тестов 'печати'.",
    emptyList()
) {
    override fun available(console: Console): Boolean =
        true

    override fun builderAvailable(flags: Map<Any?, Any?>): Boolean =
        true

    override fun action(console: Console, vararg args: Any?) {
        println("Начало обновления тестовых данных 'печати'.")
        forEachTests { dir, path ->
            val src = File("dump/$path/print")
            if (src.exists()) {
                val dist = File(dir, "test/print")
                dist.deleteOnExit()
                src.copyRecursively(dist, true)
            }
        }
        println("Обновление тестовых данных 'печати' завершено.")
    }
}