package ru.job4j;

import ru.job4j.model.Observable;

import java.util.List;

public interface Store extends AutoCloseable {
    Item addItem(Item item);
    boolean replace(Integer id, Item item);
    boolean delete(Integer id);
    List<Item> findAll();
    void findAllByReact(Observable<Item> observable);
    List<Item> findByName(String key);
    Item findById(Integer id);
}
