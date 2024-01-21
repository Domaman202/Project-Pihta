## body
Создаёт под-контекст тела функции и выполняет `выражения` в нём.

### Применение

1. `(body (expr0) (exprN))`<br>
`(expr0)` `(exprN)` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (body
            (def [[i 12]])
            (println i))
        (body
            (def [[i 21]])
            (println i))))
```