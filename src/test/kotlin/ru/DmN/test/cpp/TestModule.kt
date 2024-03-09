package ru.DmN.test.cpp

import ru.DmN.pht.utils.Platforms.CPP
import ru.DmN.test.jvm.TestModule
import ru.DmN.test.utils.TestModuleBase
import kotlin.test.Test

abstract class TestModule(dir: String) : TestModuleBase(dir, CPP) {
    @Test
    override fun testUnparse() {
        unparse()
        unparseCheck()
//        TestModule.of("${dir}/test/unparse/parsed").compileTest() // todo: мем
//        TestModule.of("${dir}/test/unparse/processed").compileTest()
    }

    override fun compileTest() {
        compile()
    }

    fun test(id: Int): String =
        ProcessBuilder("dump/$dir/main", "$id").start().run { waitFor(); String(inputStream.readBytes())  }

    companion object {
        fun of(dir: String) = object : TestModule(dir) { }
    }
}