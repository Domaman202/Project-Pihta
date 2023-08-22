package ru.DmN.pht.example.bf.test

object BFGenerator {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = readln()
        println(input[0].code)
        val output = StringBuilder()
        for (i in 0 until input[0].code)
            output.append('+')
        println(output)
    }
}