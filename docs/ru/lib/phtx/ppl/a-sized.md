## a-sized
Аттрибут `размера` применяется к __одному__ `элементу`.

### Применение

1. `(a-sized width height (element))`<br>
`width` - _ширина_.<br>
`height` - _высота_.<br>
`element` - _элемент_.

### Примеры

```pihta
(use-ctx pht phtx/ppl
    (app-fn
        (#show (presentation "Тестовая Презентация" 1000
            (page-list
                (a-sized 640 480
                    (e-image (inc-img "res/0.jpg"))))))))
```

***Требует наличие файла `0.jpg` в папке `res` исполняемого модуля.***