## e-triple
Создаёт композицию из __трёх__ `элементов`.

### Применение

1. `(e-pair e0 e1)`<br>
`e0` `e1` - _элементы_.

### Примеры

```pihta
(app-fn
    (#show (presentation "Тестовая Презентация" 1000
        (page-list
            (c-triple
                (e-image (inc-img "res/0.jpg"))
                (e-image (inc-img "res/1.jpg"))
                (e-image (inc-img "res/2.jpg")))))))
```

***Требует наличие файлов `0.jpg` `1.jpg` `2.jpg` в папке `res` исполняемого модуля.***