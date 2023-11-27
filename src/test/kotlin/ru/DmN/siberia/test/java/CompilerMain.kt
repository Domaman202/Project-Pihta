package ru.DmN.siberia.test.java

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.ClassWriter.COMPUTE_FRAMES
import org.objectweb.asm.ClassWriter.COMPUTE_MAXS
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.processor.utils.*
import ru.DmN.pht.base.utils.TypesProvider
import ru.DmN.pht.base.utils.getJavaClassVersion
import ru.DmN.pht.base.utils.readAllBytes
import java.io.File
import java.io.FileOutputStream
import java.net.URLClassLoader

object CompilerMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val source = Parser(String(CompilerMain::class.java.getResourceAsStream("/test.pht").readAllBytes())).parseNode(
            ParsingContext.base())!!
        val processor = Processor(TypesProvider.JAVA)
        val pctx = ProcessingContext.base().with(Platform.JAVA).withJCV(getJavaClassVersion())
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
        compiler.classes.values.forEach {
            if (it.name.contains('/'))
                File("dump/${it.name.substring(0, it.name.lastIndexOf('/'))}").mkdirs()
            FileOutputStream("dump/${it.name}.class").use { stream ->
                val writer = ClassWriter(COMPUTE_FRAMES + COMPUTE_MAXS)
                it.accept(writer)
                val b = writer.toByteArray()
                stream.write(b)
            }
        }
        // test
        println(Class.forName("App", true, URLClassLoader(arrayOf(File("dump").toURL()))).getMethod("main").invoke(null))
//        println(Class.forName("App").run { getMethod("main").invoke(getField("INSTANCE").get(null)) })
    }

    init {
        File("dump").mkdir()
    }
}