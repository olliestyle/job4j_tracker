package ru.job4j;

import java.util.Objects;

public class Item implements Comparable<Item> {
    private String id;
    private String name;

    public Item() {

    }
    public Item(String name) {
        this.name = name;
    }

    public Item(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Item o) {
        return id.compareTo(o.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Item item = (Item) obj;
        return name.equals(item.name) && id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
