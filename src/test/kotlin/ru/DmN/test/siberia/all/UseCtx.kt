package ru.DmN.test.siberia.all

import ru.DmN.test.Module
import kotlin.test.Test

class UseCtx : Module("test/siberia/all/use-ctx") {
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