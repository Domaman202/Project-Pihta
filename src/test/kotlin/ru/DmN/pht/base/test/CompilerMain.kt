package ru.DmN.pht.base.test

import org.objectweb.asm.ClassWriter
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.ctx.GlobalContext
import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.utils.Klass
import ru.DmN.uu.Unsafe
import java.io.FileOutputStream

object CompilerMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val compiler = Compiler()
        val node = Parser(Lexer("""
            (
                (use std)
                
                (ns ru.DmN.test (
                    (object Main (
                        (fn main (
                            (use std)
                            (#println std "Hi!")
                        ))
                    ))
                ))
            )
        """.trimIndent())
        ).parseNode()!!
        compiler.compile(node, CompilationContext(CompilationContext.Type.GLOBAL, GlobalContext(), null, null, null), false)
        while (compiler.stack.isNotEmpty()) {
            compiler.popFirstStack().forEach {
                it.compile()
            }
        }
        compiler.classes.map { it.node }.forEach {
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