## macro-inline
Встраивает значения _макро-аргументов_ (выражений типа `valn`) вместо выражений `macro-arg`.

### Применение

1. `(macro-inline [name0 nameN] expr)`<br>
`name0` `nameN` - _имена аргументов_.<br>
`expr` - _выражение_.

### Примеры

```pihta
(use-ctx pht
    (app
        (@static
            (defn foo ^void [[arg0 ^Object] [arg1 ^Object] [arg2 ^Object]]
                (println arg0 arg1 arg2)))
        (defmacro call-foo [args]
            (macro-inline [args]
                (#foo . (macro-arg args))))
        (app-fn
            (call-foo [12 21 33]))))
```