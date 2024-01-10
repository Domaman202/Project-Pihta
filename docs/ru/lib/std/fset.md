## fset
Устанавливает `значение` в `поле`.

### Применение

1. `(fset ^type field value)`<br>
`^type` - _класс_.<br>
`field` - _поле_.<br>
`value` - _значение_.<br><br>
2. `(set ^type/field value)`<br>
`^type` - _класс_.<br>
`field` - _поле_.<br>
`value` - _значение_.

### Примеры

```pihta
(use-ctx pht
    (app
        (def [[i ^int]])
        (app-fn
            (fset ^App i 202)
            (println ^App/i))))
```

```pihta
(use-ctx pht
    (app
        (def [[i ^int]])
        (app-fn
            (set ^App/i 203)
            (println ^App/i))))
```