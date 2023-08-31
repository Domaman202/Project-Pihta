## new
Создаёт `объект` `типа`.

### Применение

1. `(new ^java.lang.Object)`<br>
`java.lang.Object` - _тип_.<br><br>
2. `(new ^java.util.concurrent.atomic.AtomicInteger 12)`<br>
`java.util.concurrent.atomic.AtomicInteger` - _тип_.<br>
`12` - _аргумент_.

### Примеры

```pihta
(#println (use std/base) (new ^java.lang.Object))
```