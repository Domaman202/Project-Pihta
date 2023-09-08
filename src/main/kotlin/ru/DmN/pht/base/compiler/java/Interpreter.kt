package ru.DmN.pht.base.compiler.java

import org.objectweb.asm.ClassWriter
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.utils.Klass
import ru.DmN.pht.base.utils.compile
import ru.DmN.uu.Unsafe
import java.io.File
import kotlin.math.absoluteValue

class Interpreter {
    fun eval(code: String): Any? {
        eval0("""
            (use-ctx pht
                (obj ru.DmN.pht.tmp.TMP${code.hashCode().absoluteValue} []
                    (defn run ^void [] (${code}))))
            """).first().run {
            return getMethod("run").invoke(getField("INSTANCE").get(null))
        }
    }

    private fun eval0(code: String): List<Klass> {
        val compiler = Compiler()
        val ctx = CompilationContext.base()
        compiler.compile(code, ParsingContext.base(), ctx)
        compiler.tasks.forEach {
            ctx.stage.set(it.key)
            it.value.forEach { it() }
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