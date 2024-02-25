package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class FileCls : Module("test/pht/all/file-cls") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), "Foo!")
    }
}