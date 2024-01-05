package ru.DmN.test.pht.all

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.Module
import java.lang.reflect.Modifier
import kotlin.test.assertEquals

class FinalField : Module("test/pht/all/final-field") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), 333)
        assertEquals((test(1) as Klass).getField("I").modifiers, Modifier.PUBLIC + Modifier.STATIC + Modifier.FINAL)
    }
}