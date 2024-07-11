package org.example;

import java.util.Objects;
//Чтобы не плодить в основном классе ключами-значениями
//Отдельный класс с конструктором, сеттерами, геттерами
//На ключи (Кey) и значения (Value)
public class Key {

    private int id;

    public Key(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return id == key.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
