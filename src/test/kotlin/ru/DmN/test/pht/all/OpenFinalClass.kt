package ru.DmN.test.pht.all

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.Module
import java.lang.reflect.Modifier
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class OpenFinalClass : Module("test/pht/all/open-final-class") {
    override fun Module.compileTest() {
        compile()
        assertFalse(Modifier.isFinal((test(0) as Klass).modifiers))
        assertTrue(Modifier.isFinal((test(1) as Klass).modifiers))
    }
}