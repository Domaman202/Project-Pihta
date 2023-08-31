## valn-repeat
Создаёт `valn` путём повторения `выражения` `n` раз.

### Применение

1. `(valn-repeat (expr) n)`<br>
`(expr)` - _выражение_.<br>
`n` - _кол-во повторений_.

### Примеры

```pihta
(use std/base std/util)
(valn-repeat (#println std "Левой!") 10)
```