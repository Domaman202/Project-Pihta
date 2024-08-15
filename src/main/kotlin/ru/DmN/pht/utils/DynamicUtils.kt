package ru.DmN.pht.utils

import ru.DmN.pht.jvm.utils.vtype.DynamicReturn
import ru.DmN.pht.jvm.utils.vtype.FakeArgumentType
import java.util.*

object DynamicUtils {
    @JvmStatic
    @DynamicReturn
    @FakeArgumentType(1, "[dynamic")
    fun math(name: String, args: Array<Any?>): Any? =
        if (args.isEmpty())
            null
        else {
            val args = args.asSequence().filterNotNull()
            var first = args.first()
            when (name.uppercase()) {
                "ADD" -> {
                    when (first) {
                        is String -> {
                            val text = StringBuilder(first.length)
                            args.map(Objects::toString).forEach(text::append)
                            text
                        }

                        is Number -> args.map { if (it is Number) it.toDouble() else it.toString().toDouble() }.sum()
                        else -> invokeMethod(args.first(), "add", *args.toArray())
                    }
                }

                "SUB" -> {
                    if (first is String)
                        first = first.toDouble()
                    if (first is Number) {
                        var result = first.toDouble()
                        args.map { if (it is Number) it.toDouble() else it.toString().toDouble() }.forEach { result -= it }
                        result
                    } else invokeMethod(args.first(), "sub", *args.toArray())
                }

                "MUL" -> {
                    if (first is String)
                        first = first.toDouble()
                    if (first is Number) {
                        var result = first.toDouble()
                        args.map { if (it is Number) it.toDouble() else it.toString().toDouble() }.forEach { result *= it }
                        result
                    } else invokeMethod(args.first(), "mul", *args.toArray())
                }

                "DIV" -> {
                    if (first is String)
                        first = first.toDouble()
                    if (first is Number) {
                        var result = first.toDouble()
                        args.map { if (it is Number) it.toDouble() else it.toString().toDouble() }.forEach { result /= it }
                        result
                    } else invokeMethod(args.first(), "div", *args.toArray())
                }

                // todo: other operations

                else -> throw UnsupportedOperationException()
            }
        }

    @JvmStatic
    @FakeArgumentType(1, "[dynamic")
    fun compare(name: String, args: Array<Any?>): Boolean {
        val first = args[0]
        val second = args[1]
        return when (name.uppercase()) {
            "EQ" -> Objects.equals(first, second)
            "NOT_EQ" -> !Objects.equals(first, second)
            "GREAT" -> {
                if (first == null)
                    if (second == null)
                        false
                    else true
                else if (second == null)
                    true
                else {
                    if (first is Number) {
                        if (second is Number)
                            first.toDouble() > second.toDouble()
                        else first.toDouble() > second.toString().toDouble()
                    } else if (first is String) {
                        if (second is Number)
                            first.toDouble() > second.toDouble()
                        else first.toDouble() > second.toString().toDouble()
                    } else invokeMethod(first, "great", second) as Boolean
                }
            }
            "GREAT_OR_EQ" -> {
                if (first == null)
                    if (second == null)
                        false
                    else true
                else if (second == null)
                    true
                else {
                    if (first is Number) {
                        if (second is Number)
                            first.toDouble() >= second.toDouble()
                        else first.toDouble() >= second.toString().toDouble()
                    } else if (first is String) {
                        if (second is Number)
                            first.toDouble() >= second.toDouble()
                        else first.toDouble() >= second.toString().toDouble()
                    } else invokeMethod(first, "greatOrEquals", second) as Boolean
                }
            }
            "LESS" -> {
                if (first == null)
                    if (second == null)
                        false
                    else true
                else if (second == null)
                    true
                else {
                    if (first is Number) {
                        if (second is Number)
                            first.toDouble() < second.toDouble()
                        else first.toDouble() < second.toString().toDouble()
                    } else if (first is String) {
                        if (second is Number)
                            first.toDouble() < second.toDouble()
                        else first.toDouble() < second.toString().toDouble()
                    } else invokeMethod(first, "less", second) as Boolean
                }
            }
            "LESS_OR_EQ" -> {
                if (first == null)
                    if (second == null)
                        false
                    else true
                else if (second == null)
                    true
                else {
                    if (first is Number) {
                        if (second is Number)
                            first.toDouble() <= second.toDouble()
                        else first.toDouble() <= second.toString().toDouble()
                    } else if (first is String) {
                        if (second is Number)
                            first.toDouble() <= second.toDouble()
                        else first.toDouble() <= second.toString().toDouble()
                    } else invokeMethod(first, "lessOrEquals", second) as Boolean
                }
            }

            // todo: other operations

            else -> throw UnsupportedOperationException(name)
        }
    }

    @JvmStatic
    @DynamicReturn
    fun invokeMethod(instance: Any?, name: String, vararg args: Any?): Any? {
        if (instance == null)
            throw NullPointerException()
        val method = instance.javaClass.getDeclaredMethod(name, *args.map { it?.javaClass ?: Any::class.java }.toTypedArray())
        method.isAccessible = true
        return method.invoke(instance, *args)
    }

    @JvmStatic
    @DynamicReturn
    fun invokeGetter(instance: Any?, name: String, vararg args: Any?): Any? {
        if (instance == null)
            throw NullPointerException()
        return try {
            invokeMethod(instance, "get${name.let { it[0].uppercase() + it.substring(1) }}", args)
        } catch (ignored: NoSuchMethodException) {
            val field = instance.javaClass.getDeclaredField(name)
            field.isAccessible = true
            field.get(instance)
        }
    }

    @JvmStatic
    @DynamicReturn
    fun invokeSetter(instance: Any?, name: String, vararg args: Any?): Any? {
        if (instance == null)
            throw NullPointerException()
        return try {
            invokeMethod(instance, "set${name.let { it[0].uppercase() + it.substring(1) }}", args)
        } catch (ignored: NoSuchMethodException) {
            val field = instance.javaClass.getDeclaredField(name)
            field.isAccessible = true
            field.set(instance, args[0])
        }
    }
}