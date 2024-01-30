package ru.DmN.pht.compiler.java.ctx

import org.objectweb.asm.tree.MethodNode
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.VirtualMethod

class MethodContext(val node: MethodNode, val method: VirtualMethod, )