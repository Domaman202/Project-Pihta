package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldGetSet : Module("test/pht/all/field-get-set") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), 776)
        assertEquals(test(1), 777)
        assertEquals(test(2), 240422)
        assertEquals(test(3), 404)
    }
}