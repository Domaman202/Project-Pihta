package ru.DmN.test

import ru.DmN.uu.Unsafe
import java.lang.invoke.MethodHandle
    import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.lang.invoke.VarHandle
import java.lang.reflect.Method

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val test = Test()
        // variant A (only field)
        val getI = Unsafe.make(Unsafe.resolveOrFail(1, Test::class.java, "i", Any::class.java))
        val setI = Unsafe.make(Unsafe.resolveOrFail(3, Test::class.java, "i", Int::class.java))
        // variant B (only method)
//        val getI = Unsafe.make(Unsafe.resolve(5, Unsafe.createMemberName(Test::class.java, "getI", MethodType.methodType(Int::class.javaPrimitiveType), 5), null, 1 or 2 or 4 or 8 or 32 or 64, false))
//        val setI = Unsafe.make(Unsafe.resolve(5, Unsafe.createMemberName(Test::class.java, "setI", MethodType.methodType(Void::class.javaPrimitiveType, Int::class.javaPrimitiveType), 5), null, 1 or 2 or 4 or 8 or 32 or 64, false))
        // variant C (all)
//        val getI = Unsafe.make(Unsafe.resolve(5, Unsafe.createMemberName(Test::class.java, "getI", MethodType.methodType(Int::class.javaPrimitiveType), 5), null, 1 or 2 or 4 or 8 or 32 or 64, false).let { if (Unsafe.isResolved(it)) it else Unsafe.resolveOrFail(1, Test::class.java, "i", Int::class.javaPrimitiveType) })
//        val setI = Unsafe.make(Unsafe.resolve(5, Unsafe.createMemberName(Test::class.java, "setI", MethodType.methodType(Void::class.javaPrimitiveType, Int::class.javaPrimitiveType), 5), null, 1 or 2 or 4 or 8 or 32 or 64, false).let { if (Unsafe.isResolved(it)) it else Unsafe.make(Unsafe.resolveOrFail(3, Test::class.java, "i", Int::class.javaPrimitiveType)) })
        //

        println(getI(test))
        setI(test, 203)
        println(getI(test))
    }
}