## field
Объявляет `поля`.

### Применение

1. `(field [[i ^int]])`<br>
`[i ^int]` - _поле_.<br>
|`i` - _имя_.<br>
|`^int` - _тип_.

### Примеры

```pihta
(use-ctx std/base
    (obj App [java.lang.Object]
        (field [[i ^int]])))
```