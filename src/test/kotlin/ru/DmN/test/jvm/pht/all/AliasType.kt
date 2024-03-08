package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class AliasType : TestModule("test/pht/all/alias-type") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), Any::class.java)
        assertEquals(test(1), String::class.java)
    }
}