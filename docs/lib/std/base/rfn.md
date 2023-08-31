## rfn
Определяет `лямбду` с `внешними переменными`.

### Применение</h4>

1. `(rfn [i][[o ^java.lang.Object]] (unit))`<br>
`[i]` - _переменные_.<br>
`[[o ^java.lang.Object]]` - _аргументы_.<br>
`(unit)` - _тело_.

### Примеры

```pihta
(def [[i 12.21]])
(#invoke (rfn [i][] (#println (use std/base) i)))
```