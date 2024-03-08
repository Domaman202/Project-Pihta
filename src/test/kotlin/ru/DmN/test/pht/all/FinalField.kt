package ru.DmN.test.pht.all

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.TestModule
import java.lang.reflect.Modifier
import kotlin.test.assertEquals

class FinalField : TestModule("test/pht/all/final-field") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), 333)
        assertEquals((test(1) as Klass).getField("I").modifiers, Modifier.PUBLIC + Modifier.STATIC + Modifier.FINAL)
    }
}