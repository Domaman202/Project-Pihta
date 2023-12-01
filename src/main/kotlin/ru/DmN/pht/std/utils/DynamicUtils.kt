package ru.DmN.pht.std.utils

object DynamicUtils {
    @JvmStatic
    fun invokeMethod(instance: Any?, name: String, vararg args: Any?): Any? {
        if (instance == null)
            throw NullPointerException()
        val method = instance.javaClass.getDeclaredMethod(name, *args.map { it?.javaClass ?: Any::class.java }.toTypedArray())
        method.isAccessible = true
        return method.invoke(instance, *args)
    }

    @JvmStatic
    fun invokeGetter(instance: Any?, vararg args: Any?): Any? {
        TODO()
    }

    @JvmStatic
    fun invokeSetter(instance: Any?, vararg args: Any?): Any? {
        TODO()
    }
}