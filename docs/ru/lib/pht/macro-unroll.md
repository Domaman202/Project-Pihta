## macro-unroll
Разворачивает _макро-аргументы_ (выражений типа `valn`).

### Применение

1. `(macro-unroll [[argOut0 argIn0] [argOutN argInN]] (expr0) (exprN))`<br>
`argOut` - _выходной аргумент_.<br>
`argIn` - _входной аргумент_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (defmacro print-args [argsA argsB]
            (macro-unroll [[a argsA] [b argsB]]
                (println (macro-arg a) (macro-arg b))))
        (print-args ["202" "203" "213"] ["Первый" "Второй" "Третий"])))
```