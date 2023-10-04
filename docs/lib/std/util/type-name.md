## *cls-name\*
`Compile-time` `константа` обозначающая имя `текущего класса`.

### Применение

1. `(*cls-name*)`

### Примеры

```pihta
(use-ctx std/base std/util
    (obj App [^java.lang.Object]
        (defn main ^void []
            (#println (use std/base) (*cls-name*)))))
```