## obj
Определяет `объект`.<br>
Определяет поле `INSTANCE`.<br>
Определяет `конструктор` по умолчанию в случае его отсутствия.

### Применение

1. `(obj Test [^Any])`<br>
`Test` - _имя_.<br>
`Any` - _супер-класс_.
2. `(obj Test [^Any ^IFoo] (unit))`<br>
`Test` - _имя_.<br>
`Any` - _супер-класс_.<br>
`IFoo` - _реализуемый интерфейс_.<br>
`(unit)` - _тело_.

### Примеры

```pihta
(use-ctx std/base
    (obj App [^java.lang.Object]
        (defn main ^void []
            (#println (use std/base) "Здравствуй, товарищ!"))))
```