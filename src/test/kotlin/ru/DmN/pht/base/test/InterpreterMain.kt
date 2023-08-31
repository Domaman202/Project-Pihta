package ru.DmN.pht.base.test

import ru.DmN.pht.base.Interpreter

object InterpreterMain {
    @JvmStatic
    fun main(args: Array<String>) {
        println(Interpreter().eval(String(CompilerMain::class.java.getResourceAsStream("/test.pht").readAllBytes())))
    }
}