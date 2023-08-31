## macro-inline
Подменяет `макро`-`аргумент` в выражении на выражение.

### Применение

1. `(macro-inline [arg] (expr))`<br>
`[arg]` - _аргументы_.<br>
`(expr)` - _выражение_.

### Примеры

```pihta
(defmacro tcall [name args]
    (macro-inline [args] (mcall this (macro-name name) (macro-arg args))))
```