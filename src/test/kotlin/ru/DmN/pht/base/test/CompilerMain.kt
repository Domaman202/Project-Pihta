package ru.DmN.pht.base.test

import org.objectweb.asm.ClassWriter
import ru.DmN.pht.base.Base
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.ICompilable
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.utils.Klass
import ru.DmN.uu.Unsafe
import java.io.FileOutputStream

object CompilerMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val pctx = ParsingContext(mutableListOf(Base))
        val ctx = CompilationContext(mutableListOf(Base))
        val compiler = Compiler()
        compiler.compile("""
            (
                (use std std/collections)
                
                (ns ru.DmN.test (
                    (import [java.lang.Object Any][java.lang.String String])
                    (import-macro [pht.std.*])

                    (obj Main [^Any] (
                        (fn main ^Any [] (
                            (use std)
                            (def [arr (ints-of 12 21 0)])
                            (#println std (aset arr 2 33))
                            (unit)
                        ))
                    ))
                ))
            )
        """.trimIndent(), pctx, ctx)
        compiler.tasks.values.forEach { it.forEach(ICompilable::compile) }
        compiler.classes.map { it.second }.forEach {
            val writer = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)
            it.accept(writer)
            val b = writer.toByteArray()
            FileOutputStream("dump${it.name.substring(it.name.lastIndexOf('/') + 1)}.class").write(b)
            //
            val method = ClassLoader::class.java.getDeclaredMethod(
                "defineClass",
                ByteArray::class.java,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
            Unsafe.forceSetAccessible(method)
            method.invoke(CompilerMain::class.java.classLoader, b, 0, b.size) as Klass
        }
        println(Class.forName("ru.DmN.test.Main").run { getMethod("main").invoke(getField("INSTANCE").get(null)) } )
//        println(Class.forName("ru.DmN.test.Main").getMethod("main").invoke(null))
    }
}