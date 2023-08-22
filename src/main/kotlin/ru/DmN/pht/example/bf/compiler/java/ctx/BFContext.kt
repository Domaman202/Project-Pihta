package ru.DmN.pht.example.bf.compiler.java.ctx

import org.objectweb.asm.Label
import ru.DmN.pht.base.utils.Variable
import java.util.Stack

class BFContext(val index: Variable, val array: Variable) {
    var lastIndex: Int = 0
        set(value) {
            field = value
            if (value > maxIndex)
                maxIndex = value
        }
    var maxIndex: Int = 0
        private set
    // start / stop
    val stack: Stack<Pair<Label, Label>> = Stack()
}