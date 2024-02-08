## a-offset
Аттрибут `смещения` применяется к __одному__ `элементу`.

### Применение

1. `(a-offset up down left right (element))`<br>
`up` - _смещение сверху_.<br>
`down` - _смещение снизу_.<br>
`left` - _смещение слева_.<br>
`right` - _смещение справа_.<br>
`element` - _элемент_.

### Примеры

```pihta
(use-ctx pht phtx/ppl
    (app-fn
        (#show (presentation "Тестовая Презентация" 1000
            (page-list
                (a-offset 127 127 127 127
                    (e-image (inc-img "res/0.jpg"))))))))
```

***Требует наличие файла `0.jpg` в папке `res` исполняемого модуля.***