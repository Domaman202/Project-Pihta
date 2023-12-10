package ru.DmN.phtx.pcl

import ru.DmN.phtx.pcl.compiler.json.utils.indent
import ru.DmN.phtx.pcl.compiler.json.utils.out
import ru.DmN.phtx.pcl.laxer.Laxer
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.TypesProvider
import java.io.File

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        print()
        compileToJson()
    }

    private fun print() {
        println(Laxer(File("test/test.pc").readText()).parse(0).print())
    }

    private fun compileToJson() {
        val ctx = CompilationContext.base().apply {
            this.loadedModules += PCL // todo: CompilationContext.of(PCL)
            this.out = StringBuilder()
            this.indent = 1
        }
        val compiler = Compiler(TypesProvider.java()) // todo: какой ещё провайдер типов?! мы конфиги конвертируем >.>
        compiler.finalizers["test/json-output"] = Runnable {
            File("dump").mkdir()
            File("dump/test.json").writeText("{\n\t${ctx.out}\n}")
        }
        compiler.compile(Laxer(File("test/test.pc").readText()).parse(0), ctx)
        compiler.stageManager.runAll()
        compiler.finalizers.values.forEach { it.run() }
    }
}