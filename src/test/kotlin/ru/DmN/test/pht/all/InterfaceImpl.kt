package ru.DmN.test.pht.all

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.Module
import java.lang.reflect.Modifier
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class InterfaceImpl : Module("test/pht/all/interface-impl") {
    override fun Module.compileTest() {
        compile()
        (test(0) as Klass).let { it ->
            it.methods.find { it.name == "foo" }.let {
                assertNotNull(it)
                assertTrue(Modifier.isAbstract(it.modifiers))
            }
        }
        (test(1) as Klass).let { it ->
            assertTrue(it.interfaces.isNotEmpty())
            it.methods.find { it.name == "foo" }.let {
                assertNotNull(it)
                assertFalse(Modifier.isAbstract(it.modifiers))
            }
        }
        assertNotNull(test(2))
        assertEquals(test(3), "Foo!")
    }
}