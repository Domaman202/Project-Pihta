## efn
Определяет функцию расширения.

### Применение

1. `(efn ^typeE name ^typeR [[arg0 ^type0] [argN ^typeN]] (expr0) (exprN))`<br>
`^typeE` - _расширяемый тип_.<br>
`name` - _имя_.<br>
`^typeR` - _возвращаемый тип_.<br>
`[[arg0 ^type0] [argN ^typeN]]` - _аргументы_.<br>
`(expr0)` `(exprN)` - _тело_.<br><br>
2. `(efn [[gname0 gtype0] [gnameN gtypeN]] ^typeE name ^typeR [[arg0 ^type0] [argN ^typeN]] (expr0) (exprN))`<br>
`gname` - _имя generic-а_.<br>
`gtype` - _тип generic-а_.<br>
`^typeE` - _расширяемый тип_.<br>
`name` - _имя_.<br>
`^typeR` - _возвращаемый тип_.<br>
`arg` - _имя аргумента_<br>
`^type` - _тип аргумента_.<br>
`(expr0)` `(exprN)` - _тело_.

### Примеры

```pihta
(use-ctx pht
    (app
        (efn ^String log ^void []
            (println this))
        (app-fn
            (#log "Славься Русь!"))))
```

```pihta
(use-ctx pht
    (app
        (efn [[T ^Object]] ^String test T^ [] nil)
        (app-fn
            (println (typeof (#test<^String> "Слава России!"))))))
```