import kotlin.concurrent.thread

object SyncTest {
    var i = 0

    fun checkI(): Int {
        synchronized(this) {
            i++
            Thread.sleep(0, 10)
            i--
        }
        return i
    }
}

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        val t0 = thread { SyncTest.checkI() }
        val t1 = thread { SyncTest.checkI() }
    }
}