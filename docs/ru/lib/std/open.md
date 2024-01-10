## @open
Аннотация `open`.<br>
Применяется к `выражениям` типа:
1. `класс`
2. `метод`
3. `поле`

### Применение

1. `(@open expr0 exprN)`<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (@open
    (cls FooA [^Object]
        (ctor [] (ccall))
        (@open
        (defn foo ^void []
            (println "Foo A!")))))
    (obj FooB [^FooA]
        (ctor [] (ccall))
        (defn foo ^void []
            (println "Foo B!")))
    (app-fn
        (#foo ^FooB)))
```