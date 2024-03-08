@file:Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
package ru.DmN.pht.jvm.compiler.ctx

import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.compiler.java.ctx.BodyContext
import ru.DmN.pht.compiler.java.ctx.ClassContext
import ru.DmN.pht.compiler.java.ctx.MethodContext
import ru.DmN.pht.compiler.java.utils.NamedBlockData
import ru.DmN.pht.processor.ctx.classes
import ru.DmN.pht.processor.utils.LinkedClassesNode
import ru.DmN.pht.utils.ctx.ContextKeys
import ru.DmN.siberia.utils.ctx.IContextCollection
import ru.DmN.siberia.utils.ctx.IContextKey

inline fun <T : IContextCollection<T>> T.with(ctx: ClassContext) =
    this.with(ContextKeys.CLASS, ctx).apply { this.classes = LinkedClassesNode(this.classes, ctx.clazz) }
inline fun <T : IContextCollection<T>> T.with(ctx: MethodContext) =
    this.with(ContextKeys.METHOD, ctx)
inline fun <T : IContextCollection<T>> T.with(ctx: BodyContext) =
    this.with(ContextKeys.BODY, ctx)
inline fun <T : IContextCollection<T>> T.with(name: String, ctx: NamedBlockData) =
    this.with(ContextKeys.NAMED_BLOCKS, (this.namedBlocksOrNull?.let { HashMap(it) } ?: HashMap<String, NamedBlockData>()).apply { this[name] = ctx })
inline fun <T : IContextCollection<T>> T.with(noinline hook: () -> Unit) =
    this.with(ContextKeys.RETURN_HOOK, hook)

inline var MutableMap<IContextKey, Any?>.classes
    set(value) { this[ContextKeys.CLASSES] = value }
    get() = this[ContextKeys.CLASSES] as MutableMap<String, ClassNode>
inline val IContextCollection<*>.clazz
    get() = contexts[ContextKeys.CLASS] as ClassContext
inline val IContextCollection<*>.method
    get() = contexts[ContextKeys.METHOD] as MethodContext
inline val IContextCollection<*>.body
    get() = bodyOrNull!!
inline val IContextCollection<*>.bodyOrNull
    get() = contexts[ContextKeys.BODY] as BodyContext?
inline fun IContextCollection<*>.getNamedBlock(name: String) =
    this.namedBlocks[name]!!
inline val IContextCollection<*>.namedBlocks
    get() = this.namedBlocksOrNull!!
inline val IContextCollection<*>.namedBlocksOrNull
    get() = contexts[ContextKeys.NAMED_BLOCKS] as MutableMap<String, NamedBlockData>?
inline var IContextCollection<*>.returnHook
    set(noinline value) { contexts[ContextKeys.RETURN_HOOK] = value }
    get() = contexts[ContextKeys.RETURN_HOOK] as () -> Unit