## efn
Определяет `функцию`-`расширение`.

### Применение

1. `(efn ^java.lang.Object log ^void [] (unit))`<br>
`java.lang.Object` - _расширяемый класс_.<br>
`log` - _имя функции_.<br>
`^void` - _возвращаемый тип_.<br>
`[]` - _аргументы_.<br>
`(unit)` - _тело_.

### Примеры

```pihta
(use-ctx std/base std/macro
    (ns pht.test
        (cls Extends [^java.lang.Object]
            (efn ^java.lang.String log ^void []
                (#println (use std/base) this))))
    (ns dmn.test
        (import-extends [pht.test.Extends])
        (cls Main [^java.lang.Object]
            (@static (defn main ^void [] (#log "Пролетарии всех стран объединяйтесь!"))))
```