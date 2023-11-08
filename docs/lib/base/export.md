## export
Последовательно выполняет `выражения` в `вышестоящем` (использующим `use-ctx`) контексте.

### Применение

1. `(export (expr0) (exprN))`<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (comment "Файл исходного кода модуля 'test'.")
    (export
        (cls Test [^Object]
            (@static
            (defn foo ^void [] (println "Foo!"))))))
```

```pihta
(use-ctx pht
    (app-fn
        (#foo ^Test))
```