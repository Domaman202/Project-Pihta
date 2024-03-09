#ifndef __PHT_HPP__
#define __PHT_HPP__

#include <functional>
#include <iostream>
#include <concepts>
#include <cstdint>
#include <vector>

#ifdef PHT_GC_DUMP
#define GC_DUMP \
    { \
        std::string __pht_file_name__(__FILE_NAME__); \
        std::string __pht_line__ = std::to_string(__LINE__); \
        gc.dump(std::string(__pht_file_name__ + ", " + __pht_line__)); \
    };
#else
#define GC_DUMP
#endif

namespace dmn::pht {
    class gc;
    class object;

    class object {
        friend gc;
    protected:
        /// Поколение сборки мусора.
        uint8_t age = 0;
        /// Установка поколения сборки мусора.
        virtual void set_age(uint8_t value) {
            this->age = value;
        }

        /// Конструктор-костыль.
        explicit object(nullptr_t) { };
    public:
        /// Количество ручных ссылок на объект.
        uint16_t links_count = 0;

        /// Стандартный конструктор.
        object() = default;

        /// Функция перевода объекта в строку.
        virtual std::string toString() {
            return std::string(typeid(this).name())+"("+std::to_string((uintptr_t)this)+")";
        }
    };

    template<class T, typename std::enable_if<std::is_base_of<object, T>::value>::type* = nullptr>
    class auto_ptr {
    private:
        T* ref;
    public:
        explicit auto_ptr(T* ref) : ref(ref) {
            reinterpret_cast<object*>(ref)->links_count++;
        }

        ~auto_ptr() {
            reinterpret_cast<object*>(ref)->links_count--;
        }

        T* operator->() {
            return ref;
        }
    };

    class gc {
    private:
        /// Список объектов.
        std::vector<object*> list;
        /// Поколение сборки мусора.
        uint8_t age = 0;
        /// Порог срабатывания триггера сборки мусора.
        static constexpr size_t trigger_count = PHT_GC_MAX_SIZE / 4;
    public:
        /// Создание нового объекта.
        template<class T, typename...A, typename std::enable_if<std::is_base_of<object, T>::value>::type* = nullptr>
        T* alloc(A...args) {
            if ((list.size() + 1) % trigger_count == 0)
                collect();
            auto ref = new T(args...);
            list.push_back(ref);
            return ref;
        }

        /// Создание нового статического объекта.
        template<class T, typename...A, typename std::enable_if<std::is_base_of<object, T>::value>::type* = nullptr>
        T* alloc_static(A...args) {
            auto ref = alloc<T>(args...);
            reinterpret_cast<object*>(ref)->links_count++;
            return ref;
        }

        /// Создание нового объекта в умном указателе.
        template<class T, typename...A, typename std::enable_if<std::is_base_of<object, T>::value>::type* = nullptr>
        auto_ptr<T> alloc_ptr(A...args) {
            return auto_ptr(alloc<T>(args...));
        }

        /// Сборка мусора.
        void collect() {
            // dump
            #ifdef PHT_GC_DUMP_PRE_COLLECT
            dump("PRE COLLECT");
            #endif
            //
            age++;
            // Первый цикл прохода (пометка)
            for (auto ref : list) {
                if (ref->links_count > 0) {
                    ref->set_age(age);
                }
            }
            // Второй цикл прохода (сборка)
            for (auto iter = list.begin(); iter != list.end();) {
                auto ref = *iter;
                if (ref->age != age) {
                    list.erase(iter);
                    delete ref;
                } else ++iter;
            }
            // dump
            #ifdef PHT_GC_DUMP_POST_COLLECT
            dump("POST COLLECT");
            #endif
        }

        /// dump
        void dump(const std::string& name) {
#ifdef PHT_GC_DUMP
            std::cout << "[" << name << "] Dump (" << list.size() <<  "):" << std::endl;
            for (auto ref: list)
                std::cout << ref << " [" << (int) ref->age << "][" << ref->links_count << "]" << std::endl;
            std::cout << std::endl;
#endif
        }
    };
}

auto gc = dmn::pht::gc();

namespace dmn::pht {
    template<typename T>
    class primitive : public object {
    protected:
        const T value;
    public:
        explicit primitive(T value) : value(value) { }

        inline T toPrimitive() const {
            return value;
        }

        std::string toString() override {
            return std::to_string(value);
        }
    };
}

#endif //__PHT_HPP__
