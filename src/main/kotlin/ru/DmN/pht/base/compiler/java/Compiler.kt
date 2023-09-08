package ru.DmN.pht.base.compiler.java

import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.*
import java.lang.reflect.Modifier
import java.util.*
import kotlin.collections.HashMap

class Compiler {
    val tp = TypesProvider(this::typeOf)
    val classes: MutableList<Pair<VirtualType, ClassNode>> = ArrayList()
    val tasks: DefaultEnumMap<CompileStage, MutableList<() -> Unit>> = DefaultEnumMap(CompileStage::class.java) { ArrayList() }
    val contexts: MutableMap<String, Any?> = HashMap()

    fun calc(node: Node, ctx: CompilationContext): VirtualType? =
        this.get(ctx, node).calc(node, this, ctx)
    fun compile(node: Node, ctx: CompilationContext, ret: Boolean): Variable? =
        this.get(ctx, node).compile(node, this, ctx, ret)

    fun get(ctx: CompilationContext, node: Node): INodeCompiler<Node> {
        val name = node.tkOperation.text!!
        val i = name.lastIndexOf('/')
        if (i < 1) {
            ctx.loadedModules.forEach { it -> it.compilers.getRegex(name)?.let { return it as INodeCompiler<Node> } }
            throw RuntimeException()
        } else {
            val module = name.substring(0, i)
            return ctx.loadedModules.find { it.name == module }!!.compilers.getRegex(name.substring(i + 1)) as INodeCompiler<Node>
        }
    }

    fun pushTask(ctx: CompilationContext, stage: CompileStage, task: () -> Unit) {
        if (stage.ordinal <= ctx.stage.get().ordinal)
            task()
        else tasks[stage] += task
    }

    fun typeOf(name: String): VirtualType =
        tp.types.find { it.name == name } ?: classes.map { it.first }.find { it.name == name } ?: tp.typeOrNull(name) ?: tp.addType(klassOf(name))
}