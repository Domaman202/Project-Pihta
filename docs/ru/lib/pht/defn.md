## defn
Определяет _функцию_ / _метод_.

### Применение

1. `(defn name ^typeR [[arg0 ^type0] [argN ^typeN]] (expr0) (exprN))`<br>
`name` - _имя_.<br>
`^typeR` - _возвращаемый тип_.<br>
`arg` - _имя аргумента_<br>
`^type` - _тип аргумента_.<br>
`(expr0)` `(exprN)` - _тело_.<br><br>
2. `(defn [[gname0 gtype0] [gnameN gtypeN]] name ^typeR [[arg0 ^type0] [argN ^typeN]] (expr0) (exprN))`<br>
`gname` - _имя generic-а_.<br>
`gtype` - _тип generic-а_.<br>
`name` - _имя_.<br>
`^typeR` - _возвращаемый тип_.<br>
`arg` - _имя аргумента_<br>
`^type` - _тип аргумента_.<br>
 `(expr0)` `(exprN)` - _тело_.

### Примеры

```pihta
(use-ctx pht
    (app
        (defn foo ^void []
            (println "Foo!"))
        (app-fn
            (#foo ^App))))
```

```pihta
(use-ctx pht
    (app
        (defn [[T ^Object]] foo T^ [] nil)
        (app-fn
            (println (typeof (#foo<^String> ^App))))))
```