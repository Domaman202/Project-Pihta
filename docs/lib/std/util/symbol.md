## symbol
Собирает один `символ` из `значений` `compile-time` `выражений`.

### Применение

1. `(symbol (expr0) (exprN))`<br>
`(expr0)` `(exprN)` - _выражения_.

### Примеры

```pihta
(use std/util)
(mcall (use std/base) (symbol "print" "ln") "Здравия желаю, товарищ!")
```