package ru.DmN.pht.test.java;

import java.util.ArrayList;
import java.util.List;

public class FooImpl<B extends Number> implements IFoo<B> {
    @Override
    public B foo(B o) {
        return o;
    }
}
