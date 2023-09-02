package ru.DmN.pht.base.compiler.java

import org.objectweb.asm.ClassWriter
import ru.DmN.pht.base.Base
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.compiler.java.utils.ICompilable
import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.utils.Klass
import ru.DmN.uu.Unsafe
import java.io.File
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.absoluteValue

class Interpreter {
    fun eval(code: String): Any? {
        eval0("""
            (use-ctx std/base std/macro
                (obj ru.DmN.pht.tmp.TMP${code.hashCode().absoluteValue} []
                    (defn run ^void [] (${code}))))
            """).first().run {
            return getMethod("run").invoke(getField("INSTANCE").get(null))
        }
    }

    fun eval0(code: String): List<Klass> {
        val compiler = Compiler()
        val ctx = CompilationContext(AtomicReference(CompileStage.UNKNOWN), mutableListOf(Base))
        compiler.compile(Parser(Lexer(code)).parseNode(ParsingContext(mutableListOf(Base)))!!, ctx, false)
        compiler.tasks.forEach {
            ctx.stage.set(it.key)
            it.value.forEach(ICompilable::invoke)
        }
        return compiler.classes.map { it.second }.map {
            val writer = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)
            it.accept(writer)
            val b = writer.toByteArray()
            File("dump${it.name.replace('/', '.')}.class").outputStream().write(b)
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