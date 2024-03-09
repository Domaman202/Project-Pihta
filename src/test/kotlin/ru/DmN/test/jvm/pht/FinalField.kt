package ru.DmN.test.jvm.pht

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.jvm.TestModule
import java.lang.reflect.Modifier
import kotlin.test.assertEquals

class FinalField : TestModule("test/pht/jvm/final-field") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 333)
        assertEquals((test(1) as Klass).getField("I").modifiers, Modifier.PUBLIC + Modifier.STATIC + Modifier.FINAL)
    }
}