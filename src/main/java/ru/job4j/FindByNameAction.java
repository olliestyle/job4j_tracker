package ru.job4j;

import java.util.List;

public class FindByNameAction implements UserAction {

    private final Output output;

    public FindByNameAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "==== Find Item by Name ====";
    }

    @Override
    public boolean execute(Input input, Store store) {
        output.println(name());
        String name = input.askStr("Enter name of item to find");
        List<Item> items = store.findByName(name);
        if (items.size() == 0) {
            output.println("There is no items with this name: " + name);
        } else {
            output.println("List of items that was found: ");
            for (Item foundItem: items) {
                output.println(foundItem.getId() + " " + foundItem.getName());
            }
        }
        return true;
    }
}
