package ru.job4j;

import java.util.List;

public final class SingleMemTracker implements Store {
    private MemTracker memTracker = new MemTracker();
    private static SingleMemTracker memTrackerInstance = null;

    private SingleMemTracker() {
    }

    public static SingleMemTracker getInstance() {
        if (memTrackerInstance == null) {
            memTrackerInstance = new SingleMemTracker();
        }
        return memTrackerInstance;
    }

    @Override
    public void init() {

    }

    @Override
    public Item add(Item item) {
        return memTracker.add(item);
    }

    @Override
    public List<Item> findAll() {
        return memTracker.findAll();
    }

    @Override
    public List<Item> findByName(String key) {
        return memTracker.findByName(key);
    }

    @Override
    public Item findById(Integer id) {
        return memTracker.findById(id);
    }

    @Override
    public boolean replace(Integer id, Item item) {
        return memTracker.replace(id, item);
    }

    @Override
    public boolean delete(Integer id) {
        return memTracker.delete(id);
    }

    public static void main(String[] args) {
        SingleMemTracker singleMemTracker = SingleMemTracker.getInstance();
        SingleMemTracker secondSingleMemTracker = SingleMemTracker.getInstance();
        System.out.println(singleMemTracker == secondSingleMemTracker);
        System.out.println(singleMemTracker.equals(secondSingleMemTracker));

    }

    @Override
    public void close() throws Exception {

    }
}
