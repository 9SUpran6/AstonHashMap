package org.example;

import java.util.*;

//Прежде всего реализуем интерфейс Map с помощью команды "implements"
public class Main<K,V> implements Map<K, V>{

//Вводим константы первичного размера массива и коэффициента заполнения
//При котором массив будет увеличиваться вдвое
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

//Создаем первичный массив с size = 0, т.к. там не лежит ни одного элемента
    private Object[] array = new Object[INITIAL_CAPACITY];
    private int size = 0;

//Переписываем все методы интерфейса Map
    //Первый метод Clear создает новый массив, а старый удалит сборщик мусора,
    //т.к. на него больше не будет ссылки
    @Override
    public void clear() {
        array = new Object[INITIAL_CAPACITY];
        size = 0;
    }

    //Метод удаления по ключу
    @Override
    public boolean remove(K key) {
        //Для начала нужно получить позицию элемента в массиве
        int position = getElementPosition(key, array.length);
        //После этого объявляем что нужный нам элемент на этой позиции
        Entry existedElement = (Entry) array[position];
        //Если значение на этой позиции не null, а ключи совпадают
        //то переписываем ссылки и уменьшаем размер на 1
        if (existedElement != null && existedElement.key.equals(key)){
            array[position] = existedElement.next;
            size--;
            return true;
        //Если не null, то перебираем элементы дальше по цепочке
        } else {
            while (existedElement != null){
                Entry nextElement = existedElement.next;
                if (nextElement == null){
                    return false;
                }
                if (nextElement.key.equals(key)){
                    existedElement.next = nextElement.next;
                    size--;
                    return true;
                }
                existedElement = existedElement.next;
            }
        }
        return false;
    }
    //Метод, чтобы узнать количество заполненных ячеек
    @Override
    public int size() {
        return size;
    }
    //Метод заполнения листа значений, т.к. значения могут повторяться
    //То выбран просто ArrayList
    @Override
    public List<V> valuesList() {
        List<V> result = new ArrayList<>();
        for (Object entry : array){
            Entry existedElement = (Entry) entry;
            while (existedElement != null){
                result.add(existedElement.value);
                existedElement = existedElement.next;
            }
        }
        return result;
    }
    //Ключи уникальны, так что для их заполнения выбрана коллекция Set
    @Override
    public Set<K> keySet() {
        Set<K> result = new HashSet<>();
        for (Object entry : array){
            Entry existedElement = (Entry) entry;
            while (existedElement != null){
                result.add(existedElement.key);
                existedElement = existedElement.next;
            }
        }
        return result;
    }
    //Метод получения значения по ключу
    @Override
    public V get(K key) {
    //Для начала получаем номер позиции, затем присваиваем элементу
    //Значение по этой позиции и после проверки на equals() выдаем значение
        int position = getElementPosition(key, array.length);
        Entry existedElement = (Entry) array[position];
        while (existedElement != null){
            if (existedElement.key.equals(key)){
                return existedElement.value;
            }
            existedElement = existedElement.next;
        }
        return null;
    }
    //Метод put для добавления элемента и последующим увеличением массива.
    //Является перегруженным методом
    @Override
    public void put(K key, V value) {
        if (size >= (array.length * LOAD_FACTOR)) {
            increaseArray();
        }
        boolean put = put(key, value, array);
        if (put) {
            size++;
        }
    }
    //Метод для того, чтобы положить элемент в ячейку массива
    private boolean put(K key, V value, Object[] dst) {
    //Так же получаем позицию элемента
        int position = getElementPosition(key, dst.length);
        Entry existedElement = (Entry) dst[position];
    //Проверяем на null. Если null, то кладем в него элемент, где
    //Следующая ссылка null.
        if (existedElement == null) {
            Entry entry = new Entry(key, value, null);
            dst[position] = entry;
            return true;
    //Иначе перебираем по ссылкам элементы. Если ключи совпали, то
    //Перезаписываем значение. Иначе доходим до null элемента и
    //кладем значение в него.
        } else {
            while (true) {
                if (existedElement.key.equals(key)) {
                    existedElement.value = value;
                    return false;
                }
                if (existedElement.next == null) {
                    existedElement.next = new Entry(key, value, null);
                    return true;
                }
                existedElement = existedElement.next;
            }
        }
    }

    //Метод увеличения массива в 2 раза
    private void increaseArray() {
        Object[] newArray = new Object[array.length * 2];
    //Копируем элементы из существующего массива в newArray,
    //А после увеличения присваиваем array = newArray
        for (Object entry : array) {
            Entry existedElement = (Entry) entry;
            while (existedElement != null) {
                put(existedElement.key, existedElement.value, newArray);
                existedElement = existedElement.next;
            }
        }
        array = newArray;
    }


    //Метод получения позиции элемента
    private int getElementPosition(K key, int arrayLenght){
        return Math.abs(key.hashCode() % arrayLenght);
    }

    //Класс нода, ссылки, Entry, кому как удобнее, в которой
    //хранится ключ, значение, ссылка на следующий элемент
    private class Entry {
        private K key;
        private V value;
        private Entry next;
    //Его конструктор
        public Entry(K key, V values, Entry next) {
            this.key = key;
            this.value = values;
            this.next = next;
        }
    }
}
