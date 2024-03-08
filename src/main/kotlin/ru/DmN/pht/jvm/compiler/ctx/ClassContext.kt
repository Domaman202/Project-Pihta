package ru.DmN.pht.compiler.java.ctx

import org.objectweb.asm.tree.ClassNode
import ru.DmN.siberia.utils.vtype.VirtualType

class ClassContext(val node: ClassNode, val clazz: VirtualType)