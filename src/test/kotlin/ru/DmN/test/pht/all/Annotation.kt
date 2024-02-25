package ru.DmN.test.pht.all

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.Module
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Annotation : Module("test/pht/all/annotation") {
    override fun Module.compileTest() {
        compile()
        assertTrue((test(0) as Klass).annotations.isEmpty())
        assertEquals((test(1) as Klass).annotations.first().toString(), "@java.lang.FunctionalInterface()")
    }
}