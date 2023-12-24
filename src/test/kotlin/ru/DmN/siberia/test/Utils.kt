package ru.DmN.siberia.test

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
import ru.DmN.siberia.utils.readBytes
import java.io.File
import java.io.FileOutputStream
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.net.URLClassLoader

class Module(private val dir: String) {
    private val compile = MethodHandles.lookup().findStatic(Loader().loadClass(ModuleCompiler::class.java.name), "compile", MethodType.methodType(Void::class.javaPrimitiveType, String::class.java))

    fun compile() {
//        compile(dir)
        ModuleCompiler.compile(dir)
    }

    fun test(id: Int): Any? =
        URLClassLoader(arrayOf(File("dump/$dir").toURL())).loadClass("Test$id").getMethod("test").invoke(null)
}

class Loader : ClassLoader() {
    override fun loadClass(name: String?, resolve: Boolean): Class<*> {
        if (name!!.startsWith("ru.DmN")) {
            val bytes = super.getResourceAsStream(name.replace('.', '/') + ".class")!!.readBytes()
            return super.defineClass(name, bytes, 0, bytes.size)
        }
        return super.loadClass(name, resolve)
    }
}

object ModuleCompiler {
    @JvmStatic
    fun compile(dir: String) {
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
}