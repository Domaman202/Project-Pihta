package ru.DmN.test.console

import ru.DmN.test.console.commands.TestClear
import ru.DmN.test.console.commands.TestUpdatePrint
import ru.DmN.test.console.commands.TestUpdateUnparse
import java.io.File
import java.util.*

object TestCommands {
    val ALL_COMMANDS = listOf(TestClear, TestUpdatePrint, TestUpdateUnparse)

    @JvmStatic
    fun forEachTests(dir: File = File("src/test/resources/test"), offset: Int = dir.length().toInt() - 1, block: (File, String) -> Unit) {
        if (File(dir, "module.pht").exists())
            return block(dir, dir.path.substring(offset))
        Arrays.stream(dir.listFiles())
            .filter { it.isDirectory }
            .forEach { forEachTests(it, offset, block) }
    }
}