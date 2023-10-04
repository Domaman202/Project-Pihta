## mcall
Вызов `метода`.

### Применение

1. `(mcall std println "Кузя")`<br>
`std` - _объект_.<br>
`println` - _метод_.<br>
`"Кузя"` - _аргументы_.<br><br>

### Примеры

```pihta
(mcall (use std/base) println "Мяв!")
```

## mcall!

### Применение

1. `(#println std "Кузя")`<br>
`println` - _метод_.<br>
`std` - _объект_.<br>
`"Кузя"` - _аргументы_.<br><br>

### Примеры

```pihta
(#println (use std/base) "Мяв!")
```