package ru.DmN.pht.compiler.java.ctx

import org.objectweb.asm.tree.MethodNode
import ru.DmN.siberia.utils.vtype.VirtualMethod

class MethodContext(val node: MethodNode, val method: VirtualMethod)