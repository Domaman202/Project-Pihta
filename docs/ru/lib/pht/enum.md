## enum
Создаёт перечисление.

### Применение

1. `(enum Colors [^Object] (expr0) (exprN))`<br>
`Colors` - _имя_.<br>
`Object` - _предки_.<br>
`(expr0)` `(exprN)` - _тело_.

### Примеры

```pihta
(use-ctx pht
    (enum Colors [^Enum]
        (ector [[r ^int] [g ^int] [b ^int]]
            (set this/red r)
            (set this/green g)
            (set this/blue b))
        (fld [
            [red    ^int]
            [green  ^int]
            [blue   ^int]])
        (efld [
            [RED    255 0 0]
            [GREEN  0 255 0]
            [BLUE   0 0 255]]))
    (app-fn
        (println ^Colors/RED)))
```