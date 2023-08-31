## aset
Устанавливает `значение` `выражения` в `массив` по `индексу`.

### Применение

1. `(aset arr i (expr))`<br>
`arr` - _массив_.<br>
`i` - _индекс_.<br>
`(expr)` - _выражение_.

### Примеры

```pihta
(use std/collections)
(def [[arr (new-ints 1)]])
(aset arr 0 12)
```