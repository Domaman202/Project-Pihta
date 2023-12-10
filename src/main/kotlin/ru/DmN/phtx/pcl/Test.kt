package ru.DmN.phtx.pcl

import ru.DmN.phtx.pcl.laxer.Laxer
import java.io.File

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        parseAndPrint()
    }

    private fun parseAndPrint() {
        println(Laxer(File("test/test.pc").readText()).parse(0).print())
    }
}