package ru.DmN.pht.std.test

import org.apache.commons.lang3.time.StopWatch
import ru.DmN.pht.std.utils.mapMutable
import kotlin.test.assertNotNull

object UtilsTests {
    private val sw = StopWatch()

    @JvmStatic
    fun main(args: Array<String>) {
        sw.start()
        sw.suspend()
        //
        for (i in 0 until 10) {
            speedMyMap()
//            speedKotlinMap()
        }
        //
        println("\t${sw.formatTime()}")
    }

    private fun speedKotlinMap() {
        val src = ArrayList<Int>()
        for (i in 0..10000000)
            src += i
        sw.resume()
        val out = src.map { it.inc() }.toMutableList()
        sw.suspend()
        assertNotNull(out)
    }

    private fun speedMyMap() {
        val src = ArrayList<Int>()
        for (i in 0..10000000)
            src += i
        sw.resume()
        val out = src.mapMutable { it.inc() }
        sw.suspend()
        assertNotNull(out)
    }
}