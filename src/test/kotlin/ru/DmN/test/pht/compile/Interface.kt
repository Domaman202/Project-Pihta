package ru.DmN.test.pht.compile

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.Module
import java.lang.reflect.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class Interface {
    @Test
    fun test() {
        Module("test/pht/compile/interface").run {
            compile()
            (test(0) as Klass).let {
                assertEquals(it.name, "ITest")
                assertNull(it.superclass)
                assertTrue(Modifier.isInterface(it.modifiers))
            }
        }
    }
}