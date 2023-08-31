## use
Подключает `модули` в `текущий контекст`.<br>
Не поддерживает макросы.

### Применение

1. `(use std/base std/math)`<br>
`std/base`, `std/math` - _модули_.

### Примеры

```pihta
(use std/base)
(#println std "Здравствуй,")
(#println std "Товарищ!")
```