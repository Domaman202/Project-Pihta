package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class OverSetA : TestModule("test/pht/jvm/over-set-a") {
    override fun compileTest() {
        compile()
        assertContentEquals(test(0) as IntArray, intArrayOf(3, 3))
        assertContentEquals(test(1) as IntArray, intArrayOf(3, 7))
    }
}