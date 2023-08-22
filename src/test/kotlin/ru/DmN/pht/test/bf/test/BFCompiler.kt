package ru.DmN.pht.test.bf.test

object BFCompiler {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = readln()
        val output = StringBuilder().append("(bf ")
        input.forEach {
            output.append('(').append(
                when (it) {
                    '>' -> "next"
                    '<' -> "prev"
                    '+' -> "inc"
                    '-' -> "dec"
                    '.' -> "put"
                    '[' -> "start"
                    ']' -> "stop"
                    else -> throw RuntimeException()
                }
            ).append(')')
        }
        println(output.append(')'))
    }
}