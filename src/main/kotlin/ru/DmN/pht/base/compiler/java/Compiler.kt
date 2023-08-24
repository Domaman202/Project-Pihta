package ru.DmN.pht.base.compiler.java

import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.compiler.java.utils.ICompilable
import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.*
import java.lang.reflect.Modifier
import java.util.*
import kotlin.collections.HashMap

class Compiler {
    val types: MutableList<VirtualType> = ArrayList()
    val classes: MutableList<Pair<VirtualType, ClassNode>> = ArrayList()
    val tasks: DefaultEnumMap<CompileStage, MutableList<ICompilable>> = DefaultEnumMap(CompileStage::class.java) { ArrayList() }
    val contexts: MutableMap<String, Any?> = HashMap()

    fun calc(node: Node, ctx: CompilationContext): VirtualType? =
        this.get(ctx, node).calc(node, this, ctx)
    fun compile(code: String, pctx: ParsingContext, ctx: CompilationContext) =
        compile(Parser(Lexer(code)).parseNode(pctx)!!, ctx, false)
    fun compile(node: Node, ctx: CompilationContext, ret: Boolean): Variable? =
        this.get(ctx, node).compile(node, this, ctx, ret)

    fun get(ctx: CompilationContext, node: Node): INodeCompiler<Node> {
        val name = node.tkOperation.text!!
        val i = name.lastIndexOf('/')
        if (i == -1) {
            ctx.modules.forEach { it -> it.compilers[name]?.let { return it as INodeCompiler<Node> } }
            throw RuntimeException()
        } else {
            val module = name.substring(0, i)
            return ctx.modules.find { it.name == module }!!.compilers[name.substring(i + 1)] as INodeCompiler<Node>
        }
    }

    fun pushTask(ctx: CompilationContext, stage: CompileStage, task: ICompilable) {
        if (stage.ordinal >= ctx.stage.ordinal)
            task()
        else tasks[stage] += task
    }

    fun typeOf(klass: Klass): VirtualType =
        types.find { it.name == klass.name } ?: addType(klass)

    fun typeOf(name: String): VirtualType =
        types.find { it.name == name } ?: classes.map { it.first }.find { it.name == name } ?: typeOrNull(name) ?: addType(klassOf(name))

    private fun typeOrNull(name: String): VirtualType? {
        val type = types.find { it.name == name }
        return if (type == null && name.startsWith('['))
            typeOrNull(name.substring(1))?.let {  VirtualType(name, componentType = it) }
        else type
    }

    private fun addType(klass: Klass): VirtualType {
        val parents: MutableList<VirtualType> = ArrayList()
        klass.superclass?.let { parents.add(typeOf(it.name)) }
        Arrays.stream(klass.interfaces).map { typeOf(it.name) }.forEach(parents::add)
        val fields = ArrayList<VirtualField>()
        val methods = ArrayList<VirtualMethod>()
        return VirtualType(
            klass.name,
            parents,
            fields,
            methods,
            componentType = klass.componentType?.let(::typeOf),
            isInterface = klass.isInterface,
            final = Modifier.isFinal(klass.modifiers) || klass.isEnum
        ).apply {
            types += this
            fields += klass.declaredFields.map { VirtualField.of(this@Compiler, it) }
            methods += klass.declaredConstructors.map { VirtualMethod.of(this@Compiler, it) } +
                    klass.declaredMethods.map { VirtualMethod.of(this@Compiler, it) }
        }
    }
}