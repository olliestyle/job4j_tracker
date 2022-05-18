package ru.job4j;

public class ShowAllByReactAction implements UserAction {

    @Override
    public String name() {
        return "=== Show all items by react ===";
    }

    @Override
    public boolean execute(Input input, Store store) {
        store.findAllByReact(System.out::println);
        return true;
    }
}
