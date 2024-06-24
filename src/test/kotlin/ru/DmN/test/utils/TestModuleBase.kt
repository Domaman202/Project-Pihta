package ru.DmN.test.utils

import ru.DmN.pht.module.utils.Module
import ru.DmN.pht.module.utils.ModulesProvider
import ru.DmN.pht.std.module.ast.NodeModule
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeProcessedUse
import ru.DmN.siberia.compiler.CompilerImpl
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.console.BuildCommands
import ru.DmN.siberia.console.BuildCommands.provider
import ru.DmN.siberia.parser.ParserImpl
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.processor.ProcessorImpl
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processor.utils.with
import ru.DmN.siberia.processors.NRUseCtx.injectModules
import ru.DmN.siberia.unparser.UnparserImpl
import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.utils.IPlatform
import ru.DmN.siberia.utils.exception.BaseException
import ru.DmN.siberia.utils.vtype.TypesProvider
import java.io.File
import java.io.FileOutputStream
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class TestModuleBase(val dir: String, private val platform: IPlatform) {
    @Test
    fun testCompile() {
        try {
            compileTest()
        } catch (e: BaseException) {
            println(e.print(::provider))
            throw Error()
        }
    }

    @Test
    open fun testUnparse() {
        try {
            unparse()
            unparseCheck()
        } catch (e: BaseException) {
            println(e.print(::provider))
            throw Error()
        }
    }

    @Test
    open fun testPrint() {
        try {
            print()
            printCheck()
        } catch (e: BaseException) {
            println(e.print(::provider))
            throw Error()
        }
    }

    open fun compileTest(): Unit =
        compile()

    fun compile() {
        try {
            val mp = ModulesProvider.of(platform)
            val module = (ParserImpl(Module.getModuleFile(dir), mp).parseNode(
                ParsingContext.module(
                    platform,
                    "$dir/module.pht"
                )
            ) as NodeModule).module
            module.init(platform, mp)
            val tp = TypesProvider.of(platform)
            val processed = ArrayList<Node>()
            val processor = ProcessorImpl(mp, tp)
            val list = ArrayList<NodeProcessedUse.ProcessedData>()
            mp.injectModules(mutableListOf(module.name), list, processor, ProcessingContext.base().with(platform).apply { this.module = module })
            list.forEach { processed += it.processed; processed += it.exports }
            processor.stageManager.runAll()
            val compiler = CompilerImpl(mp, tp)
            val ctx = CompilationContext.base().with(platform).apply { this.module = module }
            processed.forEach { compiler.compile(it, ctx) }
            compiler.stageManager.runAll()
            File("dump/$dir").mkdirs()
            compiler.finalizers.forEach { it("dump/$dir") }
        } catch (e: BaseException) {
            throw WrappedException(e, BuildCommands::provider)
        }
    }

    fun unparse() {
        val mp = ModulesProvider.of(platform)
        val module = (ParserImpl(Module.getModuleFile(dir), mp).parseNode(ParsingContext.module(platform, "$dir/module.pht")) as NodeModule).module
        module.init(platform, mp)
        val tp = TypesProvider.of(platform)
        File("dump/$dir/unparse/parsed").mkdirs()
        FileOutputStream("dump/$dir/unparse/parsed/unparse.pht").use { out ->
            val unparser = UnparserImpl(mp, 1024*1024)
            val uctx = UnparsingContext.base().apply { this.platform = this@TestModuleBase.platform }
            module.nodes.forEach { unparser.unparse(it, uctx, 0) }
            out.write(unparser.out.toString().toByteArray())
        }
        val processed = ArrayList<Node>()
        val processor = ProcessorImpl(mp, tp)
        val list = ArrayList<NodeProcessedUse.ProcessedData>()
        mp.injectModules(mutableListOf(module.name), list, processor, ProcessingContext.base().with(platform).apply { this.module = module })
        list.forEach { processed += it.processed; processed += it.exports }
        processor.stageManager.runAll()
        File("dump/$dir/unparse/processed").mkdirs()
        FileOutputStream("dump/$dir/unparse/processed/unparse.pht").use { out ->
            val unparser = UnparserImpl(mp, 1024*1024)
            val uctx = UnparsingContext.base().apply { this.platform = this@TestModuleBase.platform }
            processed.forEach { unparser.unparse(it, uctx, 0) }
            out.write(unparser.out.toString().toByteArray())
        }
    }

    fun unparseCheck() {
        assertEquals(readDumpFile("unparse/parsed/unparse.pht"),    readTestFile("unparse/parsed/unparse.pht"),     "parsed.unparse.pht")
        assertEquals(readDumpFile("unparse/processed/unparse.pht"), readTestFile("unparse/processed/unparse.pht"),  "processed.unparse.pht")
    }

    private fun print() {
        val mp = ModulesProvider.of(platform)
        val module = (ParserImpl(Module.getModuleFile(dir), mp).parseNode(ParsingContext.module(platform, "$dir/module.pht")) as NodeModule).module
        module.init(platform, mp)
        val tp = TypesProvider.of(platform)
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
        val processor = ProcessorImpl(mp, tp)
        val list = ArrayList<NodeProcessedUse.ProcessedData>()
        mp.injectModules(mutableListOf(module.name), list, processor, ProcessingContext.base().with(platform).apply { this.module = module })
        list.forEach { processed += it.processed; processed += it.exports }
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

    private fun printCheck() {
        assertEquals(readDumpFile("print/parsed.short.print"),      readTestFile("print/parsed.short.print"),   "parsed.short.print")
        assertEquals(readDumpFile("print/processed.short.print"),   readTestFile("print/processed.short.print"),"processed.short.print")
        assertEquals(readDumpFile("print/parsed.long.print"),       readTestFile("print/parsed.long.print"),    "parsed.long.print")
        assertEquals(readDumpFile("print/processed.long.print"),    readTestFile("print/processed.long.print"), "processed.long.print")
    }

    private fun readDumpFile(file: String): String =
        File("dump/$dir/$file").readText()
    private fun readTestFile(file: String): String =
        String(Module::class.java.getResourceAsStream("/$dir/test/$file")!!.readBytes())
}