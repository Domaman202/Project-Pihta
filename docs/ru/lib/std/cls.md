## cls
Определяет класс.

### Применение

1. `(cls name [^type0 ^typeN] (expr0) (exprN))`<br>
`name` - _имя_.<br>
`type0` `typeN` - _предки_.<br>
`expr0` `exprN` - _тело_.<br><br>
2. `(cls [[gname0 gtype0] [gnameN gtypeN]] name [^parent0 ^parentN] (expr0) (exprN))`<br>
`gname` - _имя generic-а_.<br>
`gtype` - _тип generic-а_.<br>
`name` - _имя_.<br>
`parent0` `parentN` - _предки_.<br>
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

```pihta
(use-ctx pht
    (cls [[T ^Object]] TestA [^Object]
        (ctor [] (ccall))
        (defn foo T^ [] nil))
    (app-fn
        (println (typeof (#foo (new ^TestA<^String>))))))
```