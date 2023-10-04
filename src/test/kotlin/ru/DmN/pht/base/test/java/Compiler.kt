package ru.DmN.pht.base.test.java

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.ClassWriter.COMPUTE_FRAMES
import org.objectweb.asm.ClassWriter.COMPUTE_MAXS
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.processor.utils.JavaTypesProvider
import ru.DmN.pht.base.processor.utils.Platform
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.test.UnparserMain
import ru.DmN.pht.base.utils.Klass
import ru.DmN.pht.base.utils.with
import ru.DmN.uu.Unsafe
import java.io.FileOutputStream

object Compiler {
    @JvmStatic
    fun main(args: Array<String>) {
        val source = Parser(String(UnparserMain::class.java.getResourceAsStream("/test.pht").readAllBytes())).parseNode(ParsingContext.base())!!
        val processor = Processor(JavaTypesProvider())
        val pctx = ProcessingContext.base().with(Platform.JAVA)
        val processed = processor.process(source, pctx, ValType.NO_VALUE)!!
        processor.tasks.forEach {
            pctx.stage.set(it.key)
            it.value.forEach { it() }
        }
        val compiler = Compiler()
        val cctx = CompilationContext.base()
        compiler.compile(processed, cctx)
        compiler.tasks.forEach {
            cctx.stage.set(it.key)
            it.value.forEach { it() }
        }
        compiler.classes.forEach {
            FileOutputStream("dump.${it.name.replace('/', '.')}.class").use { stream ->
                val writer = ClassWriter(COMPUTE_FRAMES + COMPUTE_MAXS)
                it.accept(writer)
                val b = writer.toByteArray()
                stream.write(b)
                // test
                val method = ClassLoader::class.java.getDeclaredMethod(
                    "defineClass",
                    ByteArray::class.java,
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
                Unsafe.forceSetAccessible(method)
                method.invoke(this::class.java.classLoader, b, 0, b.size) as Klass
            }
        }
        // test
        println(Class.forName("App").getMethod("main").invoke(null))
//        println(Class.forName("App").run { getMethod("main").invoke(getField("INSTANCE").get(null)) })
    }
}