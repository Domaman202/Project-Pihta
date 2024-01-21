## e-pair
Создаёт `пару` `элементов`.

### Применение

1. `(e-pair e0 e1)`<br>
`e0` `e1` - _элементы_.

### Примеры

```pihta
(progn
    (import phtx/ppl (types ru.DmN.phtx.ppl.utils.Presentation))
    (app-fn
        (#setDarkTheme ^Presentation)
        (#show (presentation "Тестовая Презентация" 1000
            (page-list
                (e-pair
                    (e-pair
                        (e-image (inc-img "res/0.jpg"))
                        (e-image (inc-img "res/1.jpg")))
                    (e-pair
                        (e-image (inc-img "res/2.jpg"))
                        (e-image (inc-img "res/3.jpg"))))
                (e-pair
                    (e-pair
                        (e-image (inc-img "res/3.jpg"))
                        (e-image (inc-img "res/2.jpg")))
                    (e-pair
                        (e-image (inc-img "res/1.jpg"))
                        (e-image (inc-img "res/0.jpg")))))))))
```

***Требует наличие файлов `0.jpg` `1.jpg` `2.jpg` `3.jpg` в папке `res` исполняемого модуля.***