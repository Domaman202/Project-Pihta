package ru.DmN.test

import org.objectweb.asm.ClassWriter
import ru.DmN.pht.std.module.StdModule
import ru.DmN.pht.std.module.ast.NodeModule
import ru.DmN.pht.std.utils.type
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.Platform
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processor.utils.with
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.Module
import ru.DmN.siberia.utils.TypesProvider
import ru.DmN.siberia.utils.operation
import java.io.File
import java.io.FileOutputStream
import java.net.URLClassLoader
import kotlin.test.Test
import kotlin.test.assertTrue
import ru.DmN.test.Module as SiberiaModule

abstract class Module(private val dir: String) {
    val module = (Parser(Module.getModuleFile(dir)).parseNode(ParsingContext.of(StdModule)) as NodeModule).module

    //
    @Test
    open fun testPrint() {
        print()
        printCheck()
    }

    @Test
    open fun testUnparse() {
        unparse()
        unparseCheck()
        (object : SiberiaModule("${module.name}/unparse/parsed") { }).compileTest()
        (object : SiberiaModule("${module.name}/unparse/processed") { }).compileTest()
    }

    @Test
    open fun testCompile() {
        compileTest()
    }

    open fun SiberiaModule.compileTest() = compile()
    //

    fun unparse() {
        val tp = TypesProvider.java()
        module.init = false
        module.init()
        File("dump/$dir/unparse/parsed").mkdirs()
        FileOutputStream("dump/$dir/unparse/parsed/unparse.pht").use { out ->
            val unparser = Unparser()
            val uctx = UnparsingContext.base()
            module.nodes.forEach { unparser.unparse(it, uctx, 0) }
            out.write(unparser.out.toString().toByteArray())
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
        File("dump/$dir/unparse/processed").mkdirs()
        FileOutputStream("dump/$dir/unparse/processed/unparse.pht").use { out ->
            val unparser = Unparser()
            val uctx = UnparsingContext.base()
            processed.forEach { unparser.unparse(it, uctx, 0) }
            out.write(unparser.out.toString().toByteArray())
        }
    }

    fun unparseCheck() {
        assertTrue(String(File("dump/$dir/unparse/parsed/unparse.pht").readBytes()) ==  module.getModuleFile("unparse/parsed/unparse.pht"))
        assertTrue(String(File("dump/$dir/unparse/processed/unparse.pht").readBytes()) == module.getModuleFile("unparse/processed/unparse.pht"))
    }


    fun print() {
        val tp = TypesProvider.java()
        module.init = false
        module.init()
        File("dump/$dir/print").mkdirs()
        FileOutputStream("dump/$dir/print/parsed.short.print").use { short ->
            FileOutputStream("dump/$dir/print/parsed.long.print").use { long ->
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
        FileOutputStream("dump/$dir/print/processed.short.print").use { short ->
            FileOutputStream("dump/$dir/print/processed.long.print").use { long ->
                processed.forEach {
                    short.write(it.print(true).toByteArray())
                    short.write('\n'.code)
                    long.write(it.print(false).toByteArray())
                    long.write('\n'.code)
                }
            }
        }
    }

    fun printCheck() {
        assertTrue(String(File("dump/$dir/print/parsed.short.print").readBytes()) ==  module.getModuleFile("print/parsed.short.print"))
        assertTrue(String(File("dump/$dir/print/processed.short.print").readBytes()) == module.getModuleFile("print/processed.short.print"))
        assertTrue(String(File("dump/$dir/print/parsed.long.print").readBytes()) == module.getModuleFile("print/parsed.long.print"))
        assertTrue(String(File("dump/$dir/print/processed.long.print").readBytes()) == module.getModuleFile("print/processed.long.print"))
    }

    fun compile() {
        val tp = TypesProvider.java()
        module.init = false
        module.init()
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