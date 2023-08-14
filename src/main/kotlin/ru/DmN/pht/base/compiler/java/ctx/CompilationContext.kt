package ru.DmN.pht.base.compiler.java.ctx

class CompilationContext(val type: Type, val gctx: GlobalContext, val cctx: ClassContext?, val mctx: MethodContext?, val bctx: BodyContext?) {
    fun with(type: Type) =
        CompilationContext(type, gctx, cctx, mctx, bctx)
    fun with(gctx: GlobalContext) =
        CompilationContext(type, gctx, cctx, mctx, bctx)
    fun with(cctx: ClassContext?) =
        CompilationContext(type, gctx, cctx, mctx, bctx)
    fun with(mctx: MethodContext?) =
        CompilationContext(type, gctx, cctx, mctx, bctx)
    fun with(bctx: BodyContext?) =
        CompilationContext(type, gctx, cctx, mctx, bctx)

    enum class Type(val clazz: Boolean = false, val enum: Boolean = true, val method: Boolean = false, val body: Boolean = false) {
        GLOBAL,
        CLASS(clazz = true),
        ENUM(clazz = true, enum = true),
        CLASS_METHOD(clazz = true, method = true),
        CLASS_METHOD_BODY(clazz = true, method = true, body = true),
        METHOD(method = true),
        METHOD_BODY(method = true, body = true)
    }
}