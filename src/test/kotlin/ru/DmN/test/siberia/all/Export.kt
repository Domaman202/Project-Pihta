package ru.DmN.test.siberia.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Export : Module("test/siberia/all/export") {
    override fun Module.compileTest() {
        compile()
        assertTrue((test(0) as String).contains("export"))
        assertTrue((test(1) as String).contains("helper"))
        assertTrue((test(2) as String).contains("export"))
    }
}