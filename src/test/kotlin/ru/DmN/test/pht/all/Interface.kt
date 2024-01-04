package ru.DmN.test.pht.all

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.Module
import java.lang.reflect.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class Interface : Module("test/pht/all/interface") {
    override fun Module.compileTest() {
        compile()
        (test(0) as Klass).let {
            assertEquals(it.name, "ITest")
            assertNull(it.superclass)
            assertTrue(Modifier.isInterface(it.modifiers))
        }
    }
}