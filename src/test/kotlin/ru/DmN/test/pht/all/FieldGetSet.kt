package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class FieldGetSet : TestModule("test/pht/all/field-get-set") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), 776)
        assertEquals(test(1), 777)
        assertEquals(test(2), 240422)
        assertEquals(test(3), 404)
    }
}