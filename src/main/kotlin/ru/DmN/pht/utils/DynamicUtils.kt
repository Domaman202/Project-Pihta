package ru.DmN.pht.utils

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
    fun invokeGetter(instance: Any?, name: String, vararg args: Any?): Any? {
        if (instance == null)
            throw NullPointerException()
        return try {
            invokeMethod(instance, "get${name.let { it[0].toUpperCase() + it.substring(1) }}", args)
        } catch (ignored: NoSuchMethodException) {
            val field = instance.javaClass.getDeclaredField(name)
            field.isAccessible = true
            field.get(instance)
        }
    }

    @JvmStatic
    fun invokeSetter(instance: Any?, name: String, vararg args: Any?): Any? {
        if (instance == null)
            throw NullPointerException()
        return try {
            invokeMethod(instance, "set${name.let { it[0].toUpperCase() + it.substring(1) }}", args)
        } catch (ignored: NoSuchMethodException) {
            val field = instance.javaClass.getDeclaredField(name)
            field.isAccessible = true
            field.set(instance, args[0])
        }
    }
}