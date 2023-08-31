## efield
Определяет поле перечисления.

### Применение

1. `(efield [[name (expr0) (exprN)]])`<br>
|`name` - _имя_.<br>
|`(expr0)` `(exprN)` - _аргументы_.

### Примеры

```pihta
(use-ctx std/base std/enums
    (enum Colors [] (
        (ector [[r ^int][g ^int][b ^int]] (
            (set red    r)
            (set green  g)
            (set blue   b)))
        (@final (field [
            [red    ^int]
            [green  ^int]
            [blue   ^int]]))
        (efield [
            [RED    255 0 0]
            [GREEN  0 255 0]
            [BLUE   0 0 255]]))))
```