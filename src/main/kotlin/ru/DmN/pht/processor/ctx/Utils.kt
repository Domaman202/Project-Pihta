@file:Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
package ru.DmN.pht.processor.ctx

import ru.DmN.pht.compiler.java.utils.MacroDefine
import ru.DmN.pht.processor.utils.LinkedClassesNode
import ru.DmN.pht.utils.ctx.ContextKeys
import ru.DmN.siberia.utils.ctx.IContextCollection
import ru.DmN.siberia.utils.ctx.IContextKey
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

inline fun <T : IContextCollection<T>> T.with(ctx: GlobalContext) =
    this.with(ContextKeys.GLOBAL, ctx)
inline fun <T : IContextCollection<T>> T.with(ctx: EnumContext) =
    this.with(ContextKeys.ENUM, ctx).apply {
        this.clazz = ctx.type
        this.classes = LinkedClassesNode(this.classes, ctx.type)
        this.methodOrNull = null
        this.bodyOrNull = null
    }
inline fun <T : IContextCollection<T>> T.with(ctx: VirtualType) =
    this.with(ContextKeys.CLASS, ctx).apply {
        this.classes = LinkedClassesNode(this.classes, ctx)
        this.methodOrNull = null
        this.bodyOrNull = null
    }
inline fun <T : IContextCollection<T>> T.with(ctx: VirtualMethod?) =
    this.with(ContextKeys.METHOD, ctx)
inline fun <T : IContextCollection<T>> T.with(ctx: BodyContext) =
    this.with(ContextKeys.BODY, ctx)
inline fun <T : IContextCollection<T>> T.with(ctx: MacroContext) =
    this.with(ContextKeys.MACRO, ctx)

inline fun IContextCollection<*>.isEnum() =
    contexts.containsKey(ContextKeys.ENUM)
inline fun IContextCollection<*>.isClass() =
    contexts.containsKey(ContextKeys.CLASS) || isEnum()
inline fun IContextCollection<*>.isMethod() =
    this.methodOrNull != null
inline fun IContextCollection<*>.isBody() =
    this.bodyOrNull != null
inline fun IContextCollection<*>.isMacro() =
    contexts.containsKey(ContextKeys.MACRO)

inline var IContextCollection<*>.global
    set(value) { contexts[ContextKeys.GLOBAL] = value }
    get() = contexts[ContextKeys.GLOBAL] as GlobalContext
inline val IContextCollection<*>.enum
    get() = contexts[ContextKeys.ENUM] as EnumContext
inline var IContextCollection<*>.clazz
    set(value) { contexts[ContextKeys.CLASS] = value }
    get() = this.clazzOrNull!!
inline var IContextCollection<*>.classes
    set(value) { contexts[ContextKeys.CLASSES] = value }
    get() = contexts[ContextKeys.CLASSES] as LinkedClassesNode<VirtualType>
inline val IContextCollection<*>.clazzOrNull
    get() = contexts[ContextKeys.CLASS] as VirtualType?
inline var IContextCollection<*>.method
    set(value) { this.methodOrNull = value }
    get() = this.methodOrNull!!
inline var IContextCollection<*>.methodOrNull
    set(value) { contexts[ContextKeys.METHOD] = value }
    get() = contexts[ContextKeys.METHOD] as VirtualMethod?
inline var IContextCollection<*>.body
    set(value) { this.bodyOrNull = value }
    get() = this.bodyOrNull!!
inline var IContextCollection<*>.bodyOrNull
    set(value) { contexts[ContextKeys.BODY] = value }
    get() = contexts[ContextKeys.BODY] as BodyContext?
inline val IContextCollection<*>.macro
    get() = contexts[ContextKeys.MACRO] as MacroContext

inline var MutableMap<IContextKey, Any?>.macros
    set(value) { this[ContextKeys.MACROS] = value }
    get() = this[ContextKeys.MACROS] as MutableMap<String, MutableList<MacroDefine>>