## macro-arg-count
Вставка кол-ва `аргументов` в `аргументе` `макроса`.

### Применение

1. `(macro-arg-count name)`<br>
`name` - _имя_.

### Примеры

```pihta
(defmacro test [args]
    (macro-arg-count args))
(#println (use std/base) (test 12 21 33))
```