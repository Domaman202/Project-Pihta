## rem
Нахождение остатка от деления _нескольких_ `значений`.

### Применение

1. `(rem arg0 argN)`<br>
`arg0` `argN` - _значения_.<br><br>
2. `(% arg0 argN)`<br>
`arg0` `argN` - _значения_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (% 3 2))))
```