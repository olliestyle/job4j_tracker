package ru.job4j;

/**
 * String name() будем использовать для вывода конкретно действия при вызове меню
 */

public interface UserAction {
    String name();
    boolean execute(Input input, Store store);
}
