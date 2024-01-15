import java.lang.Thread.sleep

object SyncTest {
    var I = 0

    fun swap(): Int {
        I++
        sleep(0, 10)
        I--
        return I
    }

    fun test() {
        try {
            for (i in 0..1_000_000) {
                swap()
            }
        } catch (_: InterruptedException) {
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val th0 = Thread(::test)
        val th1 = Thread(::test)
        th0.start()
        th1.start()
        println((0..1_000).sumOf { swap() })
        th0.interrupt()
        th1.interrupt()
    }
}