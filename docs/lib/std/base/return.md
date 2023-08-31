## return
`Возврат` из `функции`.

### Применение

1. `(return)`<br><br>
2. `(return 12)`<br>
`12` - _результат функции_.

### Примеры

```pihta
(use-ctx std/base
    (obj ^App [^java.lang.Object]
        (defn main ^java.lang.Object []
            (return "Съешь ещё этих мягких французских булок, да выпей чаю."))))
```