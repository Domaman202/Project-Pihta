## progn
Последовательно выполняет `выражения`.

### Применение

1. `(progn (expr0) (exprN))`<br>
`expr0` `exprN` - _выражения_.<br><br>
2. `((expr0) (exprN))`<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (cls Foo [^Object]
        (defn foo ^void [] (progn
            (println "Foo! (1)")
            (println "Foo! (2)")))))
```