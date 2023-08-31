## new-array
Создаёт статический массив определённого `типа`.

### Применение

1. `(new-array ^java.lang.Object i)`<br>
`^java.lang.Object` - _тип_.<br>
`i` - _размер_.

### Примеры

```pihta
(use std/collections)
(#println (use std/base) (new-array ^java.lang.String 4))
```