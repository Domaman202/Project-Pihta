package ru.DmN.test.jvm

import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.test.utils.TestModuleBase
import sun.misc.Unsafe
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
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

    fun test(): String {
        val original = System.out
        val steam = ByteArrayOutputStream()
        UNSAFE.putObject(System::class.java, OUT_OFFSET, PrintStream(steam))
        URLClassLoader(arrayOf(File("dump/$dir/${dir.replace('/', '.')}.jar").toURI().toURL()))
            .loadClass("App")
            .getMethod("main", Array<String>::class.java)
            .invoke(null, emptyArray<String>())
        UNSAFE.putObject(System::class.java, OUT_OFFSET, original)
        return String(steam.toByteArray())
//        return ProcessBuilder("java", "-cp", "dump/$dir", "App").start().run { waitFor(); String(inputStream.readBytes()) }
    }

    fun test(id: Int): Any? =
        URLClassLoader(arrayOf(File("dump/$dir/${dir.replace('/', '.')}.jar").toURI().toURL()))
            .loadClass("Test$id")
            .getMethod("test")
            .invoke(null)

    companion object {
        val UNSAFE = Unsafe::class.java.getDeclaredField("theUnsafe").apply { isAccessible = true }.get(null) as Unsafe
        val OUT_OFFSET = UNSAFE.staticFieldOffset(System::class.java.getDeclaredField("out"));

        fun of(dir: String) = object : TestModule(dir) { }
    }
}