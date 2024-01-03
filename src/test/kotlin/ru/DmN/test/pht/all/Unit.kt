package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.Test

class Unit : Module("test/pht/all/unit") {
    @Test
    fun testPrint() {
        print()
        printCheck()
    }

    @Test
    fun testUnparse() {
        unparse()
        unparseCheck()
    }

    @Test
    fun testCompile() {
        compile()
    }
}