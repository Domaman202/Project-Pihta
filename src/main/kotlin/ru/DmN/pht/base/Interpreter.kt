package ru.DmN.pht.base

import org.objectweb.asm.ClassWriter
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.ICompilable
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.std.compiler.java.ctx.GlobalContext
import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.utils.Klass
import ru.DmN.uu.Unsafe

class Interpreter {
    fun eval(code: String): Any? {
        eval0("""
            (
                (use std)
                (object ru.DmN.pht.tmp.TMP${code.hashCode()}
                    (fn run ^void (${code}))
                )
            )
            """).first().run {
            return getMethod("run").invoke(getField("INSTANCE").get(null))
        }
    }

    fun eval0(code: String): List<Klass> {
        val compiler = Compiler()
        compiler.compile(Parser(Lexer(code)).parseNode(ParsingContext(mutableListOf(Base)))!!, CompilationContext(mutableListOf(Base)), false)
        compiler.tasks.values.forEach { it.forEach(ICompilable::compile) }
        return compiler.classes.map { it.node }.map {
            val writer = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)
            it.accept(writer)
            val b = writer.toByteArray()
            val method = ClassLoader::class.java.getDeclaredMethod(
                "defineClass",
                ByteArray::class.java,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
            Unsafe.forceSetAccessible(method)
            val loader = Thread.currentThread().contextClassLoader
            method.invoke(loader, b, 0, b.size) as Klass
        }
    }
}