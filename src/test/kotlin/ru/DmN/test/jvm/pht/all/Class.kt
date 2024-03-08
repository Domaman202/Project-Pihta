package ru.DmN.test.jvm.pht.all

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Class : TestModule("test/pht/all/class") {
    override fun compileTest() {
        compile()
        (test(0) as Klass).let {
            assertEquals(it.name, "Test")
            assertEquals(it.superclass, Any::class.java)
            assertTrue(it.interfaces.isEmpty())
        }
    }
}