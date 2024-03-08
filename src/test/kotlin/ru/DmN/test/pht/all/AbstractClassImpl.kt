package ru.DmN.test.pht.all

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.TestModule
import java.lang.reflect.Modifier
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AbstractClassImpl : TestModule("test/pht/all/abstract-class-impl") {
    override fun TestModule.compileTest() {
        compile()
        (test(0) as Klass).let { it ->
            assertTrue(Modifier.isAbstract(it.modifiers))
            it.methods.find { it.name == "foo" }.let {
                assertNotNull(it)
                assertTrue(Modifier.isAbstract(it.modifiers))
            }
        }
        (test(1) as Klass).let { it ->
            assertTrue(it.interfaces.isEmpty())
            it.methods.find { it.name == "foo" }.let {
                assertNotNull(it)
                assertFalse(Modifier.isAbstract(it.modifiers))
            }
        }
        assertNotNull(test(2))
        assertEquals(test(3), "Foo!")
    }
}