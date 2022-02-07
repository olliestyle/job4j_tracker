package ru.job4j;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class HbmTrackerTest {

    @Test
    public void whenAddItemThanGetByIdThanReplaceThanFindByName() {
        HbmTracker tracker = new HbmTracker();
        Item toAdd = Item.of("first", "firstItem", Timestamp.from(Instant.now()));
        tracker.addItem(toAdd);
        assertEquals(toAdd, tracker.findById(1));
        Item replace = Item.of("replace", "replacedItem", Timestamp.from(Instant.now()));
        tracker.replace(1, replace);
        assertEquals(replace.getDescription(), tracker.findByName("replace").get(0).getDescription());
    }

    @Test
    public void whenFindAllAndDelete() {
        HbmTracker tracker = new HbmTracker();
        Item first = Item.of("first", "firstItem", Timestamp.from(Instant.now()));
        Item second = Item.of("second", "secondItem", Timestamp.from(Instant.now()));
        Item third = Item.of("third", "thirdItem", Timestamp.from(Instant.now()));
        Stream.of(first, second, third).forEach(tracker::addItem);
        assertEquals(List.of(first, second, third), tracker.findAll());
        tracker.delete(2);
        assertEquals(List.of(first, third), tracker.findAll());
    }

}