package ru.DmN.pht.test.kotlin

class AdvFooImpl<T : Int> : FooImpl<T>() {
    override fun foo(o: T): T = o
}