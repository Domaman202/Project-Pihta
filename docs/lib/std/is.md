## is
Проверяет `значение` на принадлежность к `типу`.

### Применение

1. `(is ^type val)`<br>
`^type` - _тип_.<br>
`val` - _значение_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (is ^int 33)))
```