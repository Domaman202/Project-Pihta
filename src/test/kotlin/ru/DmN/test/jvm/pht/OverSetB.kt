package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class OverSetB : TestModule("test/pht/jvm/over-set-b") {
    override fun compileTest() {
        compile()
        assertContentEquals(test(0) as IntArray, intArrayOf(11, 11))
        assertContentEquals(test(1) as IntArray, intArrayOf(7, 15))
    }
}