package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapTest {
    private Map map;

    @Before
    public void setUp() throws Exception {
        map = new Main();
    }

    @Test
    //Проверка, что если вставить 100 элементов - их будет 100
    public void WhenPut100ElementsSizeBecome100() {
        for (int i =0; i < 100; i++){
            K key = new K(i);
            V value = new V("value " + i);
            map.put(key, value);
        }
        assertEquals(100, map.size());
    }
    @Test
    //Проверка, что если положить 10 разных ключей, ключей будет 10
    public void WhenPut10DifferentKeysThenSize10() {
        for (int i = 0; i < 100; i++){
            int id = i % 10;
            K key = new K(id);
            V value = new V("value " + id);
            map.put(key, value);
        }
        assertEquals(10, map.size());
    }

    @Test
    //Проверка, что количество ключей совпадает с количеством значений
    public void countOfKeysMustBeEqualsCountOfValues() {
        for (int i = 0; i < 100; i++) {
            K key = new K(i);
            V value = new V("value " + i);
            map.put(key, value);
        }
        assertEquals(100, map.size());
        assertEquals(100, map.keySet().size());
        assertEquals(100, map.valuesList().size());
    }

    @Test
    //Проверка на очистку массива
    public void Clear() {
        for (int i = 0; i < 100; i++) {
            K key = new K(i);
            V value = new V("Value " + i);
            map.put(key, value);
        }
        map.clear();
        assertEquals(0, map.size());
    }

    @Test
    //Проверка, что мы сможем достать правильное значение по ключу
    public void methodGetMustReturnRightValue() {
        for (int i = 0; i < 100; i++) {
            K key = new K(i);
            V value = new V("Value " + i);
            map.put(key, value);
        }
        K key = new K(50);
        V value = map.get(key);
        String expectedCarBrand = "Value 50";
        assertEquals(expectedCarBrand, value.getValue());
    }


    @Test
    //Проверка, что один и тот же элемент будет удален только 1 раз
    public void removeReturnTrueOnly() {
        for (int i = 0; i < 100; i++) {
            K key = new K(i);
            V value = new V("Value " + i);
            map.put(key, value);
        }
        assertEquals(100, map.size());
        K removingElement = new K(16);
        assertTrue(map.remove(removingElement));
        assertEquals(99, map.size());
        assertFalse(map.remove(removingElement));
    }

}