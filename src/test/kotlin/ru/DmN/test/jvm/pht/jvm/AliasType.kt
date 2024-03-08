package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class AliasType : TestModule("test/pht/jvm/alias-type") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), Any::class.java)
        assertEquals(test(1), String::class.java)
    }
}