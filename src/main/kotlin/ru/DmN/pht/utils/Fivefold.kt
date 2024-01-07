package ru.DmN.pht.utils

import java.io.Serializable

data class Fivefold<out A, out B, out C, out D, out E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
) : Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth $fifth)"
}