package ru.job4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemTracker implements Store {
    /**
     * Массив для хранения заявок.
     */
    private final List<Item> items = new ArrayList<>();
    private int id = 1;

    @Override
    public void init() {

    }

    /**
     * Метод добавления заявки в хранилище
     * @param item новая заявка
     */
    public Item add(Item item) {
        item.setId(id++);
        items.add(item);
        return item;
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     * @return Уникальный ключ.
     */
    private String generateId() {
        Random rand = new Random();
        return String.valueOf(rand.nextLong() + System.currentTimeMillis());
    }

    public List<Item> findAll() {
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> itemsFinded = new ArrayList<>();
        for (Item itemToFind: items) {
            if (itemToFind.getName().equals(key)) {
                itemsFinded.add(itemToFind);
            }
        }
        return  itemsFinded;
    }

    public Item findById(Integer id) {
        int indexOf = indexOf(id);
        if (indexOf == -1) {
            return null;
        } else {
            return items.get(indexOf);
        }
    }

    private int indexOf(Integer id) {
        int rsl = -1;
        for (int index = 0; index < items.size(); index++) {
            if (items.get(index).getId().equals(id)) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }

    public boolean replace(Integer id, Item item) {
        int indexOfItemToReplace = indexOf(id);
        if (indexOfItemToReplace == -1) {
            return false;
        } else {
            item.setId(id);
            items.set(indexOfItemToReplace, item);
            return true;
        }
    }

    public boolean delete(Integer id) {
        int indexOfItemToDelete = indexOf(id);
        if (indexOfItemToDelete == -1) {
            return false;
        } else {
            items.remove(indexOfItemToDelete);
            return true;
        }
    }

    @Override
    public void close() throws Exception {

    }
}
