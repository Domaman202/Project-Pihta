## continue
Переходит к концу `именованного блока`.

### Применение

1. `(continue name)`<br>
`name` - _имя блока_.

### Примеры

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