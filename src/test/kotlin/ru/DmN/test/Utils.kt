package ru.DmN.test

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
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processor.utils.with
import ru.DmN.siberia.utils.Module
import ru.DmN.siberia.utils.TypesProvider
import java.io.File
import java.io.FileOutputStream
import java.net.URLClassLoader
import kotlin.test.assertTrue

class Module(private val dir: String) {
    val module = (Parser(Module.getModuleFile(dir)).parseNode(ParsingContext.of(StdModule)) as NodeModule).module

    fun log() {
        val tp = TypesProvider.java()
        module.init()
        File("dump/$dir").mkdirs()
        FileOutputStream("dump/$dir/parsed.short.print").use { short ->
            FileOutputStream("dump/$dir/parsed.long.print").use { long ->
                module.nodes.forEach {
                    short.write(it.print(true).toByteArray())
                    short.write('\n'.code)
                    long.write(it.print(false).toByteArray())
                    long.write('\n'.code)
                }
            }
        }
        val processed = ArrayList<Node>()
        val processor = Processor(tp)
        val pctx = ProcessingContext.base().with(Platform.JAVA).apply { this.module = this@Module.module }
        module.load(processor, pctx, ValType.NO_VALUE)
        module.nodes.forEach { it ->
            processor.process(it, pctx, ValType.NO_VALUE)?.let {
                processed += it
            }
        }
        processor.stageManager.runAll()
        FileOutputStream("dump/$dir/processed.short.print").use { short ->
            FileOutputStream("dump/$dir/processed.long.print").use { long ->
                processed.forEach {
                    short.write(it.print(true).toByteArray())
                    short.write('\n'.code)
                    long.write(it.print(false).toByteArray())
                    long.write('\n'.code)
                }
            }
        }
    }

    fun logCheck() {
        assertTrue(String(File("dump/$dir/parsed.short.print").readBytes()) ==  module.getModuleFile("parsed.short.print"))
        assertTrue(String(File("dump/$dir/processed.short.print").readBytes()) == module.getModuleFile("processed.short.print"))
        assertTrue(String(File("dump/$dir/parsed.long.print").readBytes()) == module.getModuleFile("parsed.long.print"))
        assertTrue(String(File("dump/$dir/processed.long.print").readBytes()) == module.getModuleFile("processed.long.print"))
    }

    fun compile() {
        val tp = TypesProvider.java()
        val module = (Parser(Module.getModuleFile(dir)).parseNode(ParsingContext.of(StdModule)) as NodeModule).module
        module.init()
        val processed = ArrayList<Node>()
        val processor = Processor(tp)
        val pctx = ProcessingContext.base().with(Platform.JAVA).apply { this.module = module }
        module.load(processor, pctx, ValType.NO_VALUE)
        module.nodes.forEach { it ->
            processor.process(it, pctx, ValType.NO_VALUE)?.let {
                processed += it
            }
        }
        processor.stageManager.runAll()
        val compiler = Compiler(tp)
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

    fun test(id: Int): Any? =
        URLClassLoader(arrayOf(File("dump/$dir").toURL())).loadClass("Test$id").getMethod("test").invoke(null)
}