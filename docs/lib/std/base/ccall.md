## ccall
Вызов `конструктора`.

### Применение

1. `(ccall super)`<br>
`super` - _тип вызова_.<br><br>
2. `(ccall super i j)`<br>
`super` - _тип вызова_.<br>
`i` `j` - _аргументы_.<br><br>
3. `(ccall this)`<br>
`this` - _тип вызова_.<br><br>
4. `(ccall super i j)`<br>
`this` - _тип вызова_.<br>
`i` `j` - _аргументы_.<br><br>

### Примеры

```pihta
(use-ctx std/base std/macro
    (import-macro [pht.std.ctor])
    (cls App [^java.lang.Object]
        (ctor [] (ccall super))))
```