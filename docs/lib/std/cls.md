## cls
Определяет класс.

### Применение

1. `(cls name [^type0 ^typeN] (expr0) (exprN))`<br>
`name` - _имя_.<br>
`type0` `typeN` - _предки_.<br>
`expr0` `exprN` - _тело_.

### Примеры

```pihta
(use-ctx pht
    (cls Test [^Object]
        (ctor [] (ccall))
        (defn foo ^void []
            (println "Foo!")))
    (app-fn
        (#foo (new ^Test))))
```