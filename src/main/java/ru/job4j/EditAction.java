package ru.job4j;

public class EditAction implements UserAction {

    private final Output output;

    public EditAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "==== Edit Item ====";
    }

    @Override
    public boolean execute(Input input, Store store) {
        output.println(name());
        int id = input.askInt("Enter id of item to edit");
        String name = input.askStr("Set new name: ");
        boolean canBeReplaced = store.replace(id, new Item(name));
        if (canBeReplaced) {
            output.println("Item was edited");
        } else {
            output.println("There is no item with this id " + id);
        }
        return true;
    }
}
