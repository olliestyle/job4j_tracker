package ru.job4j;

import java.util.List;

public class ShowAllAction implements UserAction {
    @Override
    public String name() {
        return "==== List of all Items ====";
    }

    @Override
    public boolean execute(Input input, Store store) {
        List<Item> items = store.findAll();
        for (Item item: items) {
            System.out.println(item.getId() + " " + item.getName());
        }
        return true;
    }
}
