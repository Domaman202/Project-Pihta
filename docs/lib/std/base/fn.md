## fn
Определяет `лямбду`.

### Применение

1. `(fn [[o ^java.lang.Object]] (unit))`<br>
`[[o ^java.lang.Object]]` - _аргументы_.<br>
`(unit)` - _тело_.

### Примеры

```pihta
(#invoke (fn [] (#println (use std/base) "Слава Советскому Союзу!")))
```