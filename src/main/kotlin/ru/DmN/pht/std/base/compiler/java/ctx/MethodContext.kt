package ru.DmN.pht.std.base.compiler.java.ctx

import org.objectweb.asm.Label
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualMethod
import java.util.concurrent.atomic.AtomicInteger

class MethodContext(val node: MethodNode, val method: VirtualMethod, val nvi: AtomicInteger = AtomicInteger(), val variableStarts: MutableMap<Int, Label> = HashMap()) {
    fun createVariable(ctx: BodyContext, name: String, type: String?, label: Label): Variable =
        Variable(name, type, nvi.getAndIncrement(), false).apply {
            ctx.variables += this
            variableStarts[id] = label
        }
}