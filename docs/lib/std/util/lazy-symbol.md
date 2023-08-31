## symbol
Собирает один `ленивый`-`символ` из `значений` `compile-time` `выражений`.

### Применение

1. `(symbol (expr0) (exprN))`<br>
`(expr0)` `(exprN)` - _выражения_.

### Примеры

```pihta
(use std/base std/util std/macro)
(defmacro test []
    (use std/base)
    (macro-var [i (symbol (rand-symbol))])
    (#println std (macro-arg i))
    (#println std (macro-arg i))
    (macro-var [j (lazy-symbol (rand-symbol))])
    (#println std (macro-arg j))
    (#println std (macro-arg j)))
(test)
```