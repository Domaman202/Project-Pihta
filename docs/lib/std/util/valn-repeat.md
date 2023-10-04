## valn-repeat
Создаёт `valn` путём повторения `выражения` `n` раз.

### Применение

1. `(valn-repeat n (expr))`<br>
   `n` - _кол-во повторений_.<br>
`(expr)` - _выражение_.

### Примеры

```pihta
(use std/base std/util)
(valn-repeat 10 (#println std "Левой!"))
```