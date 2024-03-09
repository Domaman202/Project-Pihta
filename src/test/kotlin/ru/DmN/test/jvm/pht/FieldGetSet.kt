package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class FieldGetSet : TestModule("test/pht/jvm/field-get-set") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 776)
        assertEquals(test(1), 777)
        assertEquals(test(2), 240422)
        assertEquals(test(3), 404)
    }
}