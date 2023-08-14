package ru.DmN.pht.test

import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.test.java.FooImpl
import ru.DmN.pht.test.java.IFoo

object Utils {
    @JvmStatic
    fun main(args: Array<String>) {
        println(VirtualMethod.of(IFoo::class.java.methods[0]).overridableBy(VirtualMethod.of(FooImpl::class.java.methods[0]), VirtualType::ofKlass))
        //
        println(VirtualMethod.of(IFoo::class.java.methods[0]).argsc)
        println(VirtualMethod.of(FooImpl::class.java.methods[0]).argsc)
    }
}