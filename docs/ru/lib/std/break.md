## break
Переходит к началу `именованного блока`.

### Применение

1. `(break name)`<br>
`name` - _имя блока_.

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