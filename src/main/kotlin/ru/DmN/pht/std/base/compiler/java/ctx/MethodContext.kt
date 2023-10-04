package ru.DmN.pht.std.base.compiler.java.ctx

import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.utils.VirtualMethod

class MethodContext(val node: MethodNode, val method: VirtualMethod)