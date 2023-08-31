## bytes-of
Создаёт статический массив типа `byte` из `элементов`.

### Применение

1. `(bytes-of v0 vN)`<br>
`v0` `vN` - _элементы_.

### Примеры

```pihta
(use std/collections)
(#println (use std/base) (bytes-of -128 127))
```