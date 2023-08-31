## macro-var
Определение макро-переменной.

### Применение

`(macro-var name (expr))`<br>
`name` - _имя_.<br>
`(expr)` - _выражение_.

### Примеры

```pihta
(defmacro test []
    (macro-var [tvar "Тваръ"])
    (#println (use std/base) (macro-arg tvar)))
(test)
```