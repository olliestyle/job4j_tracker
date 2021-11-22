package ru.job4j;

public class FindByIDAction implements UserAction {

    private final Output output;

    public FindByIDAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "==== Find Item by ID ====";
    }

    @Override
    public boolean execute(Input input, Store store) {
        output.println(name());
        String id = input.askStr("Enter id of item to find");
        Item item = store.findById(Integer.parseInt(id));
        if (item == null) {
            output.println("There is no item with this id " + id);
        } else {
            output.println("Item was found: id " + item.getId() + ", name " + item.getName());
        }
        return true;
    }
}
