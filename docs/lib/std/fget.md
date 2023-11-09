## fget
Получает значение `поля`.

### Применение

1. `(fget ^type field)`<br>
`^type` - _класс_.<br>
`field` - _поле_.<br><br>
2. `^type/field`<br>
`^type` - _класс_.<br>
`field` - _поле_.

### Примеры

```pihta
(use-ctx pht
    (app
        (def [[i ^int]])
        (app-fn
            (set ^App/i 202)
            (println (fget ^App i)))))
```

```pihta
(use-ctx pht
    (app
        (def [[i ^int]])
        (app-fn
            (set ^App/i 213)
            (println ^App/i))))
```