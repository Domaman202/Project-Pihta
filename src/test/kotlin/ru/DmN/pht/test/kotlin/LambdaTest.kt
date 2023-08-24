package ru.DmN.pht.test.kotlin

import java.lang.Runnable

object LambdaTest {
    @JvmStatic
    fun main(args: Array<String>) {
        var str = "Hi!"
        val lambda = {
            println(str)
            str = "xD"
        }
        lambda()
        println(str)
    }
}