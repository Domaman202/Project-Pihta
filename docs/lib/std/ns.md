## ns
Создаёт ___новое__ пространство имён_ и выполняёт в нём `выражения`.

### Применение

1. `(ns ru.pht.test (expr0) (exprN))`<br>
`ru.pht.test` - _пространство имён_.<br>
`(expr0)` `(exprN)` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (ns ru.DmN.pht
        (obj Foo [^Object]
            (defn foo ^void []
                (println "Foo!"))))
    (app-fn
        (#foo ^ru.DmN.pht.Foo)))
```