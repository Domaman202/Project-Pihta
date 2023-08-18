package ru.DmN.pht.base.compiler.java

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.compiler.java.compilers.NCUse
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.ClassContext
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.compiler.java.utils.ICompilable
import ru.DmN.pht.base.compiler.java.utils.MacroDefine
import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.*
import ru.DmN.pht.std.ast.NodeDefMacro
import ru.DmN.pht.std.utils.Module
import java.lang.reflect.Modifier
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Compiler {
    val modules: MutableList<Module> = ArrayList()
    val compilers: MutableMap<String, NodeCompiler<*>> = DEFAULT_COMPILERS.toMutableMap()
    val types: MutableList<VirtualType> = ArrayList()
    val classes: MutableList<ClassContext> = ArrayList()
    val macros: MutableMap<String, MutableList<MacroDefine>> = HashMap()
    val tasks: DefaultEnumMap<CompileStage, MutableList<ICompilable>> = DefaultEnumMap(CompileStage::class.java) { ArrayList() }

    fun calc(node: Node, ctx: CompilationContext): VirtualType? =
        this[node].calc(node, this, ctx)
    fun compile(code: String, ctx: CompilationContext) =
        compile(Parser(Lexer(code)).parseNode()!!, ctx, false)
    fun compile(node: Node, ctx: CompilationContext, ret: Boolean): Variable? =
        this[node].compile(node, this, ctx, ret)
    fun <T> compute(node: Node, ctx: CompilationContext, name: Boolean): T =
        this[node].compute(node, this, ctx, name) as T
    fun applyAnnotation(node: Node, ctx: CompilationContext, annotation: Node) =
        this[node].applyAnnotation(node, this, ctx, annotation)

    operator fun get(node: Node): NodeCompiler<Node> =
        compilers[node.tkOperation.text!!] as NodeCompiler<Node>

    fun computeStringConst(node: Node, ctx: CompilationContext): String =
        if (node.isConst())
            node.getConstValueAsString()
        else {
            val result = compute<Any?>(node, ctx, true)
            if (result is String)
                result
            else computeStringConst(result as Node, ctx)
        }

    fun typeOf(klass: Klass): VirtualType =
        types.find { it.name == klass.name } ?: addType(klass)

    fun typeOf(name: String): VirtualType =
        types.find { it.name == name } ?: classes.map { it.clazz }.find { it.name == name } ?: typeOrNull(name) ?: addType(klassOf(name))

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

    companion object {
        val DEFAULT_COMPILERS: Map<String, NodeCompiler<*>>

        init {
            DEFAULT_COMPILERS = HashMap()
            // use
            DEFAULT_COMPILERS["use"] = NCUse
            // Блок
            DEFAULT_COMPILERS["progn"] = NCDefault
        }
    }
}