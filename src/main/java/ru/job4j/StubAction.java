package ru.job4j;

public class StubAction implements UserAction {
    private boolean call = false;

    @Override
    public String name() {
        return "Stub action";
    }

    @Override
    public boolean execute(Input input, Store store) {
        call = true;
        return false;
    }

    public boolean isCall() {
        return call;
    }
}
