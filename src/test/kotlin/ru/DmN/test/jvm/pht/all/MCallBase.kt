package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class MCallBase : TestModule("test/pht/all/mcall-base") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Foo!")
        assertEquals(test(1), "Foo!")
        assertEquals(test(2), "Foo Instance!")
        assertEquals(test(3), "Foo Instance!")
    }
}