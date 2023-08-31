## ector
Определение конструктора для `перечисления`.

### Применение

1. `(ector [[i ^int]] (expr0) (exprN))`<br>
`[i ^int]` - _аргумент_.<br>
|`i` - _имя_.<br>
|`^int` - _тип_.

### Примеры

```pihta
(use-ctx std/base std/enums
    (enum Colors [] (
        (ector [[r ^int][g ^int][b ^int]]
            (#println (use std/base) r g b))
        (efield [
            [RED    255 0 0]
            [GREEN  0 255 0]
            [BLUE   0 0 255]])))
```