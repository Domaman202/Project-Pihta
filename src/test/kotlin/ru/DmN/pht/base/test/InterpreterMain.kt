package ru.DmN.pht.base.test

import ru.DmN.pht.base.Interpreter

object InterpreterMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val interpreter = Interpreter()
        //
        val result = interpreter.eval("""
            (use std)
            (call std#println "Hi!")
            """.trimIndent())
        println(result)
    }
}