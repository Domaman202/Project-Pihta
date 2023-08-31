## if</h3>
Ветвление.

### Применение

1. `if (cond) (expr)`<br>
`cond` - _условие_.<br>
`expr` - _выражение_ (при условии).<br><br>
2. `if (cond) (if-expr) (else-expr)`<br>
`cond` - _условие_.<br>
`if-expr` - _выражение_ (при условии).<br>
`else-expr` - _выражение_ (иначе).

### Примеры

```pihta
(#println (use std/base) (if true 12 21))
```