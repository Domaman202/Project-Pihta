## cycle
Цикл с `условием`, последовательно выполняет `выражения`.

### Применение

1. `(cycle cond (expr0) (exprN))`<br>
`cond` - _условие_.<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (def [[i 0]])
        (cycle (< i 10)
            (println i)
            (++ i))))
```