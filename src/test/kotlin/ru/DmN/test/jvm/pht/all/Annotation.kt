package ru.DmN.test.jvm.pht.all

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Annotation : TestModule("test/pht/all/annotation") {
    override fun TestModule.compileTest() {
        compile()
        assertTrue((test(0) as Klass).annotations.isEmpty())
        assertEquals((test(1) as Klass).annotations.first().toString(), "@java.lang.FunctionalInterface()")
    }
}