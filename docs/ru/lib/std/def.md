## def
Определяет:
1. Переменную - при условии нахождения в теле метода;
2. Поле - при условии нахождения в теле класса.

### Применение

1. `(def [[^type0 name0 val0] [^typeN nameN valN]])`<br>
`^type` - _тип_.<br>
`name` - _имя_.<br>
`val` - _значение_.<br><br>
2. `(def [[name0 ^type0] [nameN ^typeN]])`<br>
`name` - _имя_.<br>
`^type` - _тип_.

### Примеры

```pihta
(use-ctx pht
    (cls Test [^Object]
        (def [[i ^int]])
        (ctor [] (ccall)))
    (app-fn
        (def [[o (new ^Test)]])
        (set o/i 12)
        (println o/i)))
```

```pihta
(use-ctx pht
    (app-fn
        (def [[^int i 21]])
        (println i)))
```

```pihta
(use-ctx pht
    (app-fn
        (def [[^int i]])
        (set i 33)
        (println i)))
```