package ru.DmN.pht.std.base.compiler.java.ctx

import org.objectweb.asm.tree.FieldNode
import ru.DmN.pht.base.utils.VirtualField

class FieldContext(val node: FieldNode, val field: VirtualField)