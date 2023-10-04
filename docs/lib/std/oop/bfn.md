# [WIP]

## bfn
Определяет `функцию`-`мост`.

### Применение

1. `(bfn foo ^void [^java.lang.Object] [^java.lang.String])`<br>
`foo` - _имя функции_.<br>
`^void` - _возвращаемый тип_.<br>
`[^java.lang.Object]` - _входные аргументы_.<br>
`[^java.lang.String]` - _выходные аргументы_.

### Примеры

```pihta
(use-ctx std/base std/macro
    (ns ru.DmN.test
        (import-macro [pht.std.ctor])
        (itf IFoo []
            (defn foo ^void [[o ^java.lang.Object]] (unit)))
        (cls FooImpl [^java.lang.Object ^IFoo] (
            (ctor (ccall super))
            (defn foo ^void [[str ^java.lang.String]]
                (#println (use std/base) "Foo!" str))
            (bfn foo ^void [^java.lang.Object] [^java.lang.String])))
        (cls Main [^java.lang.Object] (@static
            (defn main ^void []
                (#foo (new ^FooImpl) (as ^java.lang.Object "Hello!")))))))
```