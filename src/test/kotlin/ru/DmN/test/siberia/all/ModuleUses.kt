package ru.DmN.test.siberia.all

import ru.DmN.test.Module
import kotlin.test.Test

class ModuleUses : Module("test/siberia/all/module-uses") {
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