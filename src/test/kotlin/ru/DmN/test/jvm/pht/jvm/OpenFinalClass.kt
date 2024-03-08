package ru.DmN.test.jvm.pht.jvm

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.jvm.TestModule
import java.lang.reflect.Modifier
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OpenFinalClass : TestModule("test/pht/jvm/open-final-class") {
    override fun compileTest() {
        compile()
        assertFalse(Modifier.isFinal((test(0) as Klass).modifiers))
        assertTrue(Modifier.isFinal((test(1) as Klass).modifiers))
    }
}