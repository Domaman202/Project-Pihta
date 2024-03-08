package ru.DmN.test.pht.all

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.TestModule
import java.lang.reflect.Modifier
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OpenFinalClass : TestModule("test/pht/all/open-final-class") {
    override fun TestModule.compileTest() {
        compile()
        assertFalse(Modifier.isFinal((test(0) as Klass).modifiers))
        assertTrue(Modifier.isFinal((test(1) as Klass).modifiers))
    }
}