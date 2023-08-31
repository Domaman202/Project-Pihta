## import-extends
Импортирует `расширения` из `класса` `расширений`.

### Применение</h4>

1. `(import-extends [pht.test.Extends])`<br>
`pht.test.Extends` - _класс расширений_.

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