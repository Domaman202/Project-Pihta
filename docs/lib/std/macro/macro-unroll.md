## macro-unroll
Разворачивает `списки` `выражений` из `макро`-`аргументов`.

### Применение

1. `(macro-unroll [[arg args]] (expr0) (exprN))`<br>
`[[arg args]]`<br>
|`arg` - _имя_.<br>
|`args` - _аргумент_.

### Примеры

```pihta
(defmacro test [names types]
    (use std/base)
    (macro-unroll [[name names] [type types]]
        (#println std (macro-name type) (macro-name name))))
(test [i j k][^int ^long ^double])
```