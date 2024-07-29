@file:Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
package ru.DmN.pht.processor.ctx

import ru.DmN.pht.compiler.java.utils.MacroDefine
import ru.DmN.pht.processor.utils.LinkedClassesNode
import ru.DmN.pht.utils.ctx.ContextKeys.*
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.ctx.IContextCollection
import ru.DmN.siberia.utils.ctx.IContextKey
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

typealias FunctionGetType = (name: String, processor: Processor, ctx: ProcessingContext) -> VirtualType
typealias FunctionCast = (to: VirtualType, value: Node, processor: Processor, ctx: ProcessingContext) -> Node
typealias FunctionCastFrom = (from: VirtualType, to: VirtualType, value: Node, processor: Processor, ctx: ProcessingContext) -> Node

inline fun <T : IContextCollection<T>> T.with(ctx: GlobalContext): T =
    this.with(GLOBAL, ctx)
inline fun <T : IContextCollection<T>> T.with(ctx: EnumContext): T =
    this.with(ENUM, ctx).apply {
        this.clazz = ctx.type
        this.classes = LinkedClassesNode(this.classes, ctx.type)
        this.methodOrNull = null
        this.bodyOrNull = null
    }
inline fun <T : IContextCollection<T>> T.with(ctx: VirtualType): T =
    this.with(CLASS, ctx).apply {
        this.classes = LinkedClassesNode(this.classes, ctx)
        this.methodOrNull = null
        this.bodyOrNull = null
    }
inline fun <T : IContextCollection<T>> T.with(ctx: VirtualMethod?): T =
    this.with(METHOD, ctx)
inline fun <T : IContextCollection<T>> T.with(ctx: BodyContext): T =
    this.with(BODY, ctx)
inline fun <T : IContextCollection<T>> T.with(ctx: MacroContext): T =
    this.with(MACRO, ctx)

inline fun IContextCollection<*>.isClass(): Boolean =
    clazzOrNull != null
inline fun IContextCollection<*>.isMethod(): Boolean =
    methodOrNull != null
inline fun IContextCollection<*>.isBody(): Boolean =
    bodyOrNull != null
inline fun IContextCollection<*>.isMacro(): Boolean =
    contexts.containsKey(MACRO)

inline var IContextCollection<*>.global
    set(value) { contexts[GLOBAL] = value }
    get() = contexts[GLOBAL] as GlobalContext
inline val IContextCollection<*>.classes_seq
    get() = (contexts[CLASSES] as LinkedClassesNode<VirtualType>).asSequence()
inline var IContextCollection<*>.classes
    set(value) { contexts[CLASSES] = value }
    get() = contexts[CLASSES] as LinkedClassesNode<VirtualType>
inline val IContextCollection<*>.enum
    get() = contexts[ENUM] as EnumContext
inline var IContextCollection<*>.clazz
    set(value) { contexts[CLASS] = value }
    get() = this.clazzOrNull!!
inline val IContextCollection<*>.clazzOrNull
    get() = contexts[CLASS] as VirtualType?
inline var IContextCollection<*>.method
    set(value) { this.methodOrNull = value }
    get() = this.methodOrNull!!
inline var IContextCollection<*>.methodOrNull
    set(value) { contexts[METHOD] = value }
    get() = contexts[METHOD] as VirtualMethod?
inline var IContextCollection<*>.body
    set(value) { this.bodyOrNull = value }
    get() = this.bodyOrNull!!
inline var IContextCollection<*>.bodyOrNull
    set(value) { contexts[BODY] = value }
    get() = contexts[BODY] as BodyContext?
inline val IContextCollection<*>.macro
    get() = contexts[MACRO] as MacroContext
var IContextCollection<*>.getType
    set(value) { contexts[F_GET_TYPE] = value }
    inline get() = contexts[F_GET_TYPE] as FunctionGetType
var IContextCollection<*>.cast
    set(value) { contexts[F_CAST] = value }
    inline get() = contexts[F_CAST] as FunctionCast
var IContextCollection<*>.castFrom
    set(value) { contexts[F_CAST_FROM] = value }
    inline get() = contexts[F_CAST_FROM] as FunctionCastFrom

inline var MutableMap<IContextKey, Any?>.macros
    set(value) { this[MACROS] = value }
    get() = this[MACROS] as MutableMap<String, MutableList<MacroDefine>>