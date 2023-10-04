## while
`Цикл` с `предусловием`.

### Применение

1. `(while (cond) (expr0) (exprN))`<br>
`cond` - _условие_.<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use std/base std/math)
(def [[^long i 0l]])
(while (< i 10l)
    (#println std i)
    (set i (+ i 1l)))
```