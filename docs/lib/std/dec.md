## dec
__Декриментирует__ значение `переменной`.

### Применение

1. `(dec var)`<br>
`var` - _переменная_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (def [[i 0]])
        (dec i)
        (println i)))
```