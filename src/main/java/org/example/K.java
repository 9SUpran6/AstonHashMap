package org.example;

import java.util.Objects;
//Чтобы не плодить в основном классе ключами-значениями
//Отдельный класс с конструктором, сетерами, геттерами
//На ключи (К) и значения (V)
public class K {
    private int id;

    public K(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
//Для корректной работы Хэш-таблиц необходимо переопределять
//Как equals(), так и hashCode(). Что и было сделано
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        K k = (K) o;
        return id == k.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
