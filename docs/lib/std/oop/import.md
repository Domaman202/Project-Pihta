## import-extends
Импортирует `класс`.

### Применение

1. `(import [java.lang.System])`<br>
`java.lang.System` - _класс_.<br><br>
2. `(import [java.lang.Object Any])`<br>
`java.lang.Object` - _класс_.<br>
`Any` - _имя класса_.

### Примеры

```pihta
(import [java.lang.Object Any])
(#println (use std/base) ^Any)
```