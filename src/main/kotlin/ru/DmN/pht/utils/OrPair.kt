package ru.DmN.pht.utils

class OrPair<A, B>(var first: A?, var second: B?) {
    val isFirst
        get() = this.first != null

    fun first() =
        first!!
    fun second() =
        second!!

    override fun toString(): String =
        "OrPair($first, $second)"

    companion object {
        fun <A, B> first(first: A) =
            OrPair<A, B>(first, null)
        fun <A, B> second(second: B) =
            OrPair<A, B>(null, second)
    }
}