
object SyncTest {
    fun test() {
        synchronized(this) {
            println("hi!")
        }
    }
}