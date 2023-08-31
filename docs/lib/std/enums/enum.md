## enum
Определяет `перечисление`.

### Применение

1. `(enum Test [])`<br>
`Test` - _имя_.<br>
`[]` - _реализуемые интерфейсы_.
2. `(enum Test [^IFoo] (expr))`<br>
`Test` - _имя_.<br>
`[^IFoo]` - _реализуемые интерфейсы_.<br>
`(expr)` - _тело_.

### Примеры

```pihta
(use-ctx std/base std/enums
    (enum Colors [] (
        (ector [])
        (efield [[RED] [GREEN] [BLUE]]))))
```