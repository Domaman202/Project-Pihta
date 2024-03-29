package ru.DmN.test.console

import ru.DmN.siberia.console.BaseCommands
import ru.DmN.siberia.console.Console
import ru.DmN.siberia.console.utils.Command
import java.io.File
import java.util.*

object TestCommands {
    val TEST_CLEAR = Command(
        "test-clear",
        "tc",
        "Тест",
        "Очистка тестовых данных",
        "Удаляет все данные тестов у всех тестов.",
        emptyList(),
        BaseCommands::alviseAvailable,
        TestCommands::testClear
    )

    val TEST_UPDATE_PRINT = Command(
        "test-update-print",
        "tup",
        "Тест",
        "Обновление 'печати'",
        "Обновляет данные тестов 'печати'.",
        emptyList(),
        BaseCommands::alviseAvailable,
        TestCommands::testUpdatePrint
    )

    val TEST_UPDATE_UNPARSE = Command(
        "test-update-unparse",
        "tuu",
        "Тест",
        "Обновление 'де-парсинга'",
        "Обновляет данные тестов 'де-парсинга'.",
        emptyList(),
        BaseCommands::alviseAvailable,
        TestCommands::testUpdateUnparse
    )

    val ALL_COMMANDS = listOf(TEST_CLEAR, TEST_UPDATE_PRINT, TEST_UPDATE_UNPARSE)

    @JvmStatic
    fun testUpdateUnparse(console: Console, vararg args: Any?) {
        println("Начало обновления тестовых данных 'де-парсинга'.")
        forEachTests { dir, path ->
            val src = File("dump/$path/unparse")
            if (src.exists()) {
                val dist = File(dir, "test/unparse")
                dist.deleteOnExit()
                src.copyRecursively(dist, true)
                dist.listFiles()!!.forEach {
                    it.mkdirs()
                    File(it, "module.pht").writeText("""
                        (module
                            (name "$path/test/unparse/${it.name}")
                            (version "1.0.0")
                            (author "DomamaN202")
                            (deps ["pht"])
                            (src ["unparse.pht"]))
                    """.trimIndent())
                }
            }
        }
        println("Обновление тестовых данных 'де-парсинга' завершено.")
    }

    @JvmStatic
    fun testUpdatePrint(console: Console, vararg args: Any?) {
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

    @JvmStatic
    fun testClear(console: Console, vararg args: Any?) {
        println("Начало очистки всех тестовых данных.")
        forEachTests { dir, _ ->
            println(dir)
            File(dir, "test").deleteRecursively()
        }
        println("Очистка всех тестовых данных завершено.")
    }

    fun forEachTests(dir: File = File("src/test/resources/test"), offset: Int = dir.length().toInt() - 1, block: (File, String) -> Unit) {
        if (File(dir, "module.pht").exists())
            return block(dir, dir.path.substring(offset))
        Arrays.stream(dir.listFiles())
            .filter { it.isDirectory }
            .forEach { forEachTests(it, offset, block) }
    }
}