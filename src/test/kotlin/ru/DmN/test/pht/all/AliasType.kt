package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class AliasType : TestModule("test/pht/all/alias-type") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), Any::class.java)
        assertEquals(test(1), String::class.java)
    }
}