## array-of-type
Создаёт `массив` из `элементов` с определённым `типом`.

### Применение

1. `(array-of ^type (expr0) (exprN))`<br>
`type` - _тип_.<br>
`(expr0)` `(exprN)` - _элементы_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (array-of-type ^Object 12 21 33))))
```