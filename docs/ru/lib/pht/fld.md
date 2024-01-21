## fld
Определяет поля, создаёт геттеры и сеттеры.

### Применение

1. `(fld [[name0 ^type0] [nameN ^typeN]])`<br>
`name` - _имя_.<br>
`^type` - _тип_.

### Примеры

```pihta
(use-ctx pht
    (cls Test [^Object]
        (fld [[i ^int]])
        (ctor [] (ccall)))
    (app-fn
        (def [[o (new ^Test)]])
        (set o/i 12)
        (println o/i)))
```