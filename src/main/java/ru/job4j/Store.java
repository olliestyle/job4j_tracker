package ru.job4j;

import java.util.List;

public interface Store extends AutoCloseable {
    void init();
    Item addItem(Item item);
    boolean replace(Integer id, Item item);
    boolean delete(Integer id);
    List<Item> findAll();
    List<Item> findByName(String key);
    Item findById(Integer id);
}
