@file:Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
package ru.DmN.pht.jvm.compiler.ctx

import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.compiler.java.ctx.BodyContext
import ru.DmN.pht.compiler.java.ctx.ClassContext
import ru.DmN.pht.compiler.java.ctx.MethodContext
import ru.DmN.pht.compiler.java.utils.NamedBlockData
import ru.DmN.pht.module.utils.Module
import ru.DmN.pht.processor.ctx.classes
import ru.DmN.pht.processor.utils.LinkedClassesNode
import ru.DmN.pht.utils.ctx.ContextKeys.*
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.utils.ctx.IContextCollection
import ru.DmN.siberia.utils.ctx.IContextKey

inline fun CompilationContext.addCompiledClass(compiler: Compiler, name: String, node: ClassNode) {
    (compiledClassesOrNull ?: compiler.contexts.compiledClasses.getOrPut(module) { HashMap() }.apply { compiledClassesOrNull = this })[name] = node
}

inline fun <T : IContextCollection<T>> T.with(ctx: ClassContext) =
    this.with(CLASS, ctx).apply { this.classes = LinkedClassesNode(this.classes, ctx.clazz) }
inline fun <T : IContextCollection<T>> T.with(ctx: MethodContext) =
    this.with(METHOD, ctx)
inline fun <T : IContextCollection<T>> T.with(ctx: BodyContext) =
    this.with(BODY, ctx)
inline fun <T : IContextCollection<T>> T.with(name: String, ctx: NamedBlockData) =
    this.with(NAMED_BLOCKS, (this.namedBlocksOrNull?.let { HashMap(it) } ?: HashMap<String, NamedBlockData>()).apply { this[name] = ctx })
inline fun <T : IContextCollection<T>> T.with(noinline hook: () -> Unit) =
    this.with(RETURN_HOOK, hook)

inline val MutableMap<IContextKey, Any?>.compiledClasses
    get() = compiledClassesOrNull!!
inline var MutableMap<IContextKey, Any?>.compiledClassesOrNull
    set(value) { this[COMPILED_CLASSES] = value }
    get() = this[COMPILED_CLASSES] as MutableMap<Module, MutableMap<String, ClassNode>>?
inline val IContextCollection<*>.compiledClasses
    get() = compiledClassesOrNull!!
inline var IContextCollection<*>.compiledClassesOrNull
    set(value) { contexts[COMPILED_CLASSES] = value }
    get() = contexts[COMPILED_CLASSES] as MutableMap<String, ClassNode>?
inline val IContextCollection<*>.clazz
    get() = contexts[CLASS] as ClassContext
inline val IContextCollection<*>.method
    get() = contexts[METHOD] as MethodContext
inline val IContextCollection<*>.body
    get() = bodyOrNull!!
inline val IContextCollection<*>.bodyOrNull
    get() = contexts[BODY] as BodyContext?
inline fun IContextCollection<*>.getNamedBlock(name: String) =
    this.namedBlocks[name]!!
inline val IContextCollection<*>.namedBlocks
    get() = this.namedBlocksOrNull!!
inline val IContextCollection<*>.namedBlocksOrNull
    get() = contexts[NAMED_BLOCKS] as MutableMap<String, NamedBlockData>?
inline var IContextCollection<*>.returnHook
    set(noinline value) { contexts[RETURN_HOOK] = value }
    get() = contexts[RETURN_HOOK] as () -> Unit