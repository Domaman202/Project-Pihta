package ru.DmN.test.jvm

import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.test.utils.TestModuleBase
import java.io.File
import java.net.URLClassLoader
import kotlin.test.Test

abstract class TestModule(dir: String) : TestModuleBase(dir, JVM) {
    @Test
    override fun testUnparse() {
        unparse()
        unparseCheck()
        of("${dir}/test/unparse/parsed").compileTest()
        of("${dir}/test/unparse/processed").compileTest()
    }

    fun test(id: Int): Any? =
        URLClassLoader(arrayOf(File(dumpDir).toURI().toURL())).loadClass("Test$id").getMethod("test").invoke(null)

    companion object {
        fun of(dir: String) = object : TestModule(dir) { }
    }
}