## macro-arg
Производит вставку _макро-аргумента_.

### Применение

1. `(macro-arg name)`<br>
`name` - _имя_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (def-macro log [data]
            (println (macro-arg data)))
        (log 240222)))
```