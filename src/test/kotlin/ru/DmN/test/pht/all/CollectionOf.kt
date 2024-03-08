package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertTrue

class CollectionOf : TestModule("test/pht/all/collection-of") {
    override fun TestModule.compileTest() {
        compile()
        assertTrue((test(0) as IntArray).contentEquals(intArrayOf(12, 21, 33)))
        assertTrue((test(1) as LongArray).contentEquals(longArrayOf(202L, 203L, 213L)))
        assertTrue((test(2) as List<*>).containsAll(listOf("Слава", "России")))
    }
}