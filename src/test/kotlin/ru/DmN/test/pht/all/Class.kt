package ru.DmN.test.pht.all

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.Module
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Class : Module("test/pht/all/class") {
    override fun Module.compileTest() {
        compile()
        (test(0) as Klass).let {
            assertEquals(it.name, "Test")
            assertEquals(it.superclass, Any::class.java)
            assertTrue(it.interfaces.isEmpty())
        }
    }
}