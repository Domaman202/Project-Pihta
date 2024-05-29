package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ArrCompType : TestModule("test/pht/jvm/arr-comp-type") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), Array<Any>::class.java)
        assertEquals(test(1), Any::class.java)
    }
}