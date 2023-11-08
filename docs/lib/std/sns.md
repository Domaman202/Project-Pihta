## sns
Создаёт __вложенное__ пространство имён и выполняёт в нём `выражения`.

### Применение

1. `(sns test (expr0) (exprN))`<br>
`test` - _пространство имён_.<br>
`(expr0)` `(exprN)` - _выражения_.

### Примеры

```
(use-ctx pht
    (ns ru.DmN.pht
        (sns test
            (cls Foo [^Object]
                (defn foo ^void []
                    (println "Foo!"))))))
```