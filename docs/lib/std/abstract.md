## @abstract
Аннотация `abstract`.<br>
Применяется к выражениям типа:
1. `класс`
2. `метод`

### Применение

1. `(@abstract expr0 exprN)`<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (@abstract
        (cls Foo [^Object]
            (ctor [] (ccall))
            (defn foo ^void []
                (println "Foo!"))))
    (obj FooImpl [^Foo]
        (ctor [] (ccall)))
    (app-fn
        (#foo ^FooImpl)))
```

```pihta
(use-ctx pht
    (itf IFoo [] (@abstract
        (defn foo ^void [])))
    (obj FooImpl [^Object ^IFoo]
        (ctor [] (ccall))
        (defn foo ^void []
            (println "Foo!")))
    (app-fn
        (#foo ^FooImpl)))
```