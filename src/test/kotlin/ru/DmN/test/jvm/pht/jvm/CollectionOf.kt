package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertTrue

class CollectionOf : TestModule("test/pht/jvm/collection-of") {
    override fun compileTest() {
        compile()
        assertTrue((test(0) as IntArray).contentEquals(intArrayOf(12, 21, 33)))
        assertTrue((test(1) as LongArray).contentEquals(longArrayOf(202L, 203L, 213L)))
        assertTrue((test(2) as List<*>).containsAll(listOf("Слава", "России")))
    }
}