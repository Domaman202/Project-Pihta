## defmacro
Определяет `макрос`.

### Применение

1. `(defmacro name [arg0 argsN] (expr0) (exprN))`<br>
`name` - _имя_.<br>
`[arg0 argsN]` - _аргументы_.<br>
`(expr0)` `(exprN)` - _выражения_.

### Примеры

```pihta
(defmacro log [data]
    (#println (use std/base) (macro-arg data)))
(log "Слава Советскому Союзу!")
```