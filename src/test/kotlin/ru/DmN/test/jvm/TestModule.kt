package ru.DmN.test.jvm

import ru.DmN.pht.module.utils.Module
import ru.DmN.pht.module.utils.ModulesProvider
import ru.DmN.pht.std.module.ast.NodeModule
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.CompilerImpl
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.parser.ParserImpl
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.processor.ProcessorImpl
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processor.utils.with
import ru.DmN.siberia.processors.NRUseCtx.injectModules
import ru.DmN.siberia.utils.vtype.TypesProvider
import ru.DmN.test.utils.TestModuleBase
import java.io.File
import java.net.URLClassLoader
import kotlin.test.Test

abstract class TestModule(dir: String) : TestModuleBase(dir, JVM) {
    @Test
    override fun testUnparse() {
        unparse()
        unparseCheck()
        (object : TestModule("${dir}/test/unparse/parsed") { }).compileTest()
        (object : TestModule("${dir}/test/unparse/processed") { }).compileTest()
    }

    @Test
    open fun testCompile() {
        compileTest()
    }

    open fun compileTest() =
        compile()

    fun compile() {
        val mp = ModulesProvider.of(JVM)
        val module = (ParserImpl(Module.getModuleFile(dir), mp).parseNode(ParsingContext.module(JVM)) as NodeModule).module
        module.init(JVM, mp)
        val tp = TypesProvider.of(JVM)
        val processed = ArrayList<Node>()
        val processor = ProcessorImpl(mp, tp)
        mp.injectModules(
            mutableListOf(module.name),
            processed,
            processed,
            processor,
            ProcessingContext.base().with(JVM).apply { this.module = module }
        )
        processor.stageManager.runAll()
        val compiler = CompilerImpl(mp, tp)
        val ctx = CompilationContext.base().apply { this.platform = JVM }
        processed.forEach { compiler.compile(it, ctx) }
        compiler.stageManager.runAll()
        File("dump/$dir").mkdirs()
        compiler.finalizers.forEach { it("dump/$dir") }
    }

    fun test(id: Int): Any? =
        URLClassLoader(arrayOf(File("dump/$dir").toURI().toURL())).loadClass("Test$id").getMethod("test").invoke(null)
}