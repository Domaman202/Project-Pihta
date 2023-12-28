package ru.DmN.test.pht.compile

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Class {
    @Test
    fun test() {
        Module("test/pht/compile/class").run {
            compile()
            (test(0) as Klass).let {
                assertEquals(it.name, "Test")
                assertEquals(it.superclass, Any::class.java)
                assertTrue(it.interfaces.isEmpty())
            }
        }
    }
}