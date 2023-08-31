## progn
Последовательно выполняет `выражения`.

### Применение

1. `(progn (expr0) (exprN))`<br>
`expr0` `exprN` - _выражения_.<br><br>
2. `((expr0) (exprN))`<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(progn
    (use std/base)
    (#println std "Здравствуй,")
    (#println std "Товарищ!"))
```