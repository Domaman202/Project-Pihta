package ru.DmN.pht.std.test

import org.objectweb.asm.ClassWriter
import ru.DmN.pht.std.module.StdModule
import ru.DmN.pht.std.module.ast.NodeModule
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.Platform
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.with
import ru.DmN.siberia.utils.Module
import ru.DmN.siberia.utils.TypesProvider
import java.io.File
import java.io.FileOutputStream
import java.net.URLClassLoader

fun compileModule(dir: String) {
    val module = (Parser(Module.getModuleFile(dir)).parseNode(ParsingContext.of(StdModule)) as NodeModule).module
    val processed = ArrayList<Node>()
    val processor = Processor(TypesProvider.JAVA)
    val pctx = ProcessingContext.base().with(Platform.JAVA)
    processed += module.load(processor, pctx, ValType.NO_VALUE)!!
    processor.stageManager.runAll()
    val compiler = Compiler(TypesProvider.JAVA)
    val cctx = CompilationContext.base()
    processed.forEach { compiler.compile(it, cctx) }
    compiler.stageManager.runAll()
    compiler.finalizers.forEach { it.value.run() }
    File("dump/$dir").mkdirs()
    compiler.classes.values.forEach {
        if (it.name.contains('/'))
            File("dump/$dir/${it.name.substring(0, it.name.lastIndexOf('/'))}").mkdirs()
        FileOutputStream("dump/$dir/${it.name}.class").use { stream ->
            val writer = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)
            it.accept(writer)
            val b = writer.toByteArray()
            stream.write(b)
        }
    }
}

fun runModule(dir: String, id: Int): Any? =
    URLClassLoader(arrayOf(File("dump/$dir").toURL())).loadClass("Test$id").getMethod("test").invoke(null)