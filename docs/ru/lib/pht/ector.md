## ector
Определяет конструктор перечисления.

### Применение

1. `(ector [[arg0 ^type0] [argN ^typeN]] (expr0) (exprN))`<br>
   `[[arg0 ^type0] [argN ^typeN]]` - _аргументы_.<br>
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