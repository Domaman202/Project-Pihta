## inc
__Инкрементирует__ значение `переменной`.

### Применение

1. `(inc var)`<br>
`var` - _переменная_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (def [[i 0]])
        (inc i)
        (println i)))
```