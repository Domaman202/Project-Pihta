package ru.DmN.test.jvm.pht.all

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.jvm.TestModule
import java.lang.reflect.Modifier
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class Interface : TestModule("test/pht/all/interface") {
    override fun compileTest() {
        compile()
        (test(0) as Klass).let {
            assertEquals(it.name, "ITest")
            assertNull(it.superclass)
            assertTrue(Modifier.isInterface(it.modifiers))
        }
    }
}