package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class OverSetC : TestModule("test/pht/jvm/over-set-c") {
    override fun compileTest() {
        compile()
        assertContentEquals(test(0) as IntArray, intArrayOf(19, 19))
        assertContentEquals(test(1) as IntArray, intArrayOf(11, 23))
    }
}