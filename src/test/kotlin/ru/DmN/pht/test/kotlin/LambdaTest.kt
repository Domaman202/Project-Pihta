package ru.DmN.pht.test.kotlin

object LambdaTest {
    @JvmStatic
    fun main(args: Array<String>) {
        val lambda = { println("Hi!") }
        lambda()
    }
}