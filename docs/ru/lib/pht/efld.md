## efld
Определяет экземпляры перечисления.

### Применение

1. `(efld [[name0 arg0 argN] [nameN arg0 argN]])`<br>
`name` - _имя_.<br>
`arg0` `argN` - _аргументы_. 

### Примеры

```pihta
(use-ctx pht
    (enum Colors [^Object]
        (ector [[r ^int] [g ^int] [b ^int]]
            (ccall)
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