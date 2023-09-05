package ru.DmN.pht.base.test.java

import ru.DmN.pht.base.compiler.java.Interpreter

object InterpreterMain {
    @JvmStatic
    fun main(args: Array<String>) {
        println(Interpreter().eval(String(InterpreterMain::class.java.getResourceAsStream("/test.pht").readAllBytes())))
    }
}