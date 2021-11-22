package ru.job4j;

public class DeleteAction implements UserAction {

    private final Output output;

    public DeleteAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "==== Delete Item ====";
    }

    @Override
    public boolean execute(Input input, Store store) {
        output.println(name());
        String id = input.askStr("Enter id of item to delete");
        boolean canBeDeleted = store.delete(id);
        if (canBeDeleted) {
            output.println("Item was deleted");
        } else {
            output.println("There is no item with this id " + id);
        }
        return true;
    }
}
