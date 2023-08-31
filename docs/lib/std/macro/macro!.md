## macro!
Вставка макроса.

### Применение

`(name (expr0) (exprN))`<br>
`name` - _имя_.<br>
`(expr0)` `(exprN)` - _аргументы_.

### Примеры

```pihta
(defmacro log [data]
    (#println (use std/base) (macro-arg data)))
(log "#РаботайтеБратья")
```