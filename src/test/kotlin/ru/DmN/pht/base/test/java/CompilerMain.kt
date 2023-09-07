package ru.DmN.pht.base.test.java

import org.objectweb.asm.ClassWriter
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
        val ctx = CompilationContext.base()
        val compiler = Compiler()
        compiler.compile(
            String(CompilerMain::class.java.getResourceAsStream("/test.pht").readAllBytes()),
            ParsingContext.base(),
            ctx
        )
        compiler.tasks.forEach {
            ctx.stage.set(it.key)
            it.value.forEach(ICompilable::invoke)
        }
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
        println(Class.forName("App").run { getMethod("main").invoke(getField("INSTANCE").get(null)) })
//        println(Class.forName("App").getMethod("main").invoke(null))
    }
}