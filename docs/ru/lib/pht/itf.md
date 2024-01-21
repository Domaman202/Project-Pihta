## itf
Определяет интерфейс.

### Применение

1. `(itf name [^type0 ^typeN] (expr0) (exprN))`<br>
`name` - _имя_.<br>
`type0` `typeN` - _предки_.<br>
`expr0` `exprN` - _тело_.

### Примеры

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