package ru.DmN.test.pht.compile

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.Module
import java.lang.reflect.Modifier
import kotlin.test.*

class AbstractClassImpl {
    @Test
    fun test() {
        Module("test/pht/compile/abstract-class-impl").run {
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
}