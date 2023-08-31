## *ns-name\*
`Compile-time` `константа` обозначающая имя `текущего пространство имён`.

### Применение

1. `(*ns-name*)`

### Примеры

```pihta
(use std/util)
(ns test
    (#println (use std/base) (*ns-name*)))
```