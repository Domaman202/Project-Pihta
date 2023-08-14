package ru.DmN.pht.test.kotlin

open class FooImpl<T : Number> : IFoo<T> {
    override fun foo(o: T): T = o
}