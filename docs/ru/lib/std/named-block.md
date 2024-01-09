## named-block
Именованный блок `выражений`.<br>
Последовательно выполняет выражения.<br>
Имеет `имя`, предоставляет возможность работы с `break`, `continue`.

### Применение

1. `(named-block name expr0 exprN)`<br>
`name` - _имя_.<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (named-block CycleBlock
            (def [[i 0]])
            (cycle (< i 10)
                (if (> i 5)
                    (break CycleBlock))
                (println i)
                (++ i)))))
```

```pihta
(use-ctx pht
    (app-fn
        (def [[i 0]])
        (named-block CycleBlock
            (cycle (< i 10)
                (++ i)
                (if (< i 5)
                    (continue CycleBlock))
                (println i)))))
```