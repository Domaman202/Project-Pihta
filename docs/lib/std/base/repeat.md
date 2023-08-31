## repeat
`Цикл` с `постусловием`.

### Применение

1. `(repeat (cond) (expr0) (exprN))`<br>
`cond` - _условие_.<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use std/base std/math)
(def [[^long i 10l]])
(repeat (< i 10l)
    (#println std i)
    (set i (+ i 1l)))
```