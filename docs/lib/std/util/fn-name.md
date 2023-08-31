## *fn-name\*
`Compile-time` `константа` обозначающая имя `текущей функции`.

### Применение

1. `(*fn-name*)`

### Примеры

```pihta
(use-ctx std/base std/util
    (obj App [^java.lang.Object]
        (defn main ^void []
            (#println (use std/base) (*fn-name*)))))
```