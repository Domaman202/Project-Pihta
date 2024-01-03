package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertTrue

class CollectionOf : Module("test/pht/all/collection-of") {
    override fun Module.compileTest() {
        compile()
        assertTrue((test(0) as IntArray).contentEquals(intArrayOf(12, 21, 33)))
        assertTrue((test(1) as LongArray).contentEquals(longArrayOf(202L, 203L, 213L)))
        assertTrue((test(2) as List<*>).containsAll(listOf("Слава", "России")))
    }
}