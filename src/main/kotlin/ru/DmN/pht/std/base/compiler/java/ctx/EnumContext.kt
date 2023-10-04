package ru.DmN.pht.std.base.compiler.java.ctx

import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.processor.ctx.EnumConstContext

class EnumContext(node: ClassNode, type: VirtualType, val enums: MutableList<EnumConstContext> = ArrayList()) : ClassContext(node, type)