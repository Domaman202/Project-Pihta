package ru.DmN.test.utils

import ru.DmN.pht.module.utils.Module
import ru.DmN.pht.module.utils.ModulesProvider
import ru.DmN.pht.std.module.ast.NodeModule
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.parser.ParserImpl
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.processor.ProcessorImpl
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processor.utils.with
import ru.DmN.siberia.processors.NRUseCtx.injectModules
import ru.DmN.siberia.unparser.UnparserImpl
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.IPlatform
import ru.DmN.siberia.utils.vtype.TypesProvider
import java.io.File
import java.io.FileOutputStream
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class TestModuleBase(val dir: String, val platform: IPlatform) {
    @Test
    open fun testPrint() {
        print()
        printCheck()
    }

    @Test
    open fun testUnparse() {
        unparse()
        unparseCheck()
    }

    fun unparse() {
        val mp = ModulesProvider.of(platform)
        val module = (ParserImpl(Module.getModuleFile(dir), mp).parseNode(ParsingContext.module(platform)) as NodeModule).module
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
        mp.injectModules(
            mutableListOf(module.name),
            processed,
            processed,
            processor,
            ProcessingContext.base().with(platform).apply { this.module = module }
        )
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
        val module = (ParserImpl(Module.getModuleFile(dir), mp).parseNode(ParsingContext.module(platform)) as NodeModule).module
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
        mp.injectModules(
            mutableListOf(module.name),
            processed,
            processed,
            processor,
            ProcessingContext.base().with(platform).apply { this.module = module }
        )
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

    protected fun readDumpFile(file: String) =
        File("dump/$dir/$file").readText()
    protected fun readTestFile(file: String) =
        String(Module::class.java.getResourceAsStream("/$dir/test/$file")!!.readBytes())
}