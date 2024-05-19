package ru.DmN.test.utils

import ru.DmN.siberia.utils.exception.BaseException
import java.io.InputStream
import java.io.PrintStream
import java.io.PrintWriter

class WrappedException(private val exception: BaseException, private val provider: (String) -> InputStream) : Exception() {
    override val message: String
        get() = exception.print(provider)

    override fun printStackTrace() =
        exception.printStackTrace()
    override fun printStackTrace(s: PrintStream?) =
        exception.printStackTrace(s)
    override fun printStackTrace(s: PrintWriter?) =
        exception.printStackTrace(s)
}