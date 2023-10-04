## as
Проверяет принадлежность `значения` `выражения` к `типу`.

### Применение

1. `(is ^java.lang.String "Text")`<br>
   `java.lang.String` - _тип_.<br>
   `"Text"` - _выражение_.

### Примеры

```pihta
(#println (use std/base) (is ^java.lang.String "ZOV"))
```