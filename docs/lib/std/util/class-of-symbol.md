## class-of-symbol
`Compile-time` `константа` получающая класс по имени из `значения` `compile-time` `выражения.

### Применение

1. `(class-of-symbol (expr))`<br>
`(expr)` - _выражение_.

### Примеры

```pihta
(use std/base std/util)
(#println std (class-of-symbol (symbol "java.lang" "." "Object")))
```