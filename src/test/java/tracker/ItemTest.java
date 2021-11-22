package tracker;

import ru.job4j.Item;
import ru.job4j.SortByNameItem;
import ru.job4j.SortByNameItemInReverseOrder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ItemTest {
    @Test
    public void whenAscendingOrderCompareById() {
        Item item1 = new Item("AAA");
        item1.setId("a");
        Item item2 = new Item("BBB");
        item2.setId("b");
        Item item3 = new Item("CCC");
        item3.setId("c");
        Item item4 = new Item("DDD");
        item4.setId("d");
        List<Item> list = new ArrayList<>();
        list.add(item4);
        list.add(item3);
        list.add(item1);
        list.add(item2);
        Collections.sort(list);
        List<Item> expectedList = new ArrayList<>();
        expectedList.add(item1);
        expectedList.add(item2);
        expectedList.add(item3);
        expectedList.add(item4);
        assertThat(list, is(expectedList));
    }

    @Test
    public void whenDescendingOrderCompareById() {
        Item item1 = new Item("AAA");
        item1.setId("a");
        Item item2 = new Item("BBB");
        item2.setId("b");
        Item item3 = new Item("CCC");
        item3.setId("c");
        Item item4 = new Item("DDD");
        item4.setId("d");
        List<Item> list = new ArrayList<>();
        list.add(item4);
        list.add(item3);
        list.add(item1);
        list.add(item2);
        Collections.sort(list, Collections.reverseOrder());
        List<Item> expectedList = new ArrayList<>();
        expectedList.add(item4);
        expectedList.add(item3);
        expectedList.add(item2);
        expectedList.add(item1);
        assertThat(list, is(expectedList));
    }

    @Test
    public void whenAscendingOrderCompareByName() {
        Item item1 = new Item("AAA");
        item1.setId("z");
        Item item2 = new Item("BBB");
        item2.setId("y");
        Item item3 = new Item("CCC");
        item3.setId("w");
        Item item4 = new Item("DDD");
        item4.setId("x");
        List<Item> list = new ArrayList<>();
        list.add(item4);
        list.add(item3);
        list.add(item1);
        list.add(item2);
        Collections.sort(list, new SortByNameItem());
        List<Item> expectedList = new ArrayList<>();
        expectedList.add(item1);
        expectedList.add(item2);
        expectedList.add(item3);
        expectedList.add(item4);
        assertThat(list, is(expectedList));
    }

    @Test
    public void whenDescendingOrderCompareByName() {
        Item item1 = new Item("AAA");
        item1.setId("z");
        Item item2 = new Item("BBB");
        item2.setId("y");
        Item item3 = new Item("CCC");
        item3.setId("w");
        Item item4 = new Item("DDD");
        item4.setId("x");
        List<Item> list = new ArrayList<>();
        list.add(item4);
        list.add(item3);
        list.add(item1);
        list.add(item2);
        Collections.sort(list, new SortByNameItem().reversed());
        List<Item> expectedList = new ArrayList<>();
        expectedList.add(item4);
        expectedList.add(item3);
        expectedList.add(item2);
        expectedList.add(item1);
        assertThat(list, is(expectedList));
    }

    @Test
    public void whenDescendingOrderCompareByNameWithoutReversed() {
        Item item1 = new Item("AAA");
        item1.setId("z");
        Item item2 = new Item("BBB");
        item2.setId("y");
        Item item3 = new Item("CCC");
        item3.setId("w");
        Item item4 = new Item("DDD");
        item4.setId("x");
        List<Item> list = new ArrayList<>();
        list.add(item4);
        list.add(item3);
        list.add(item1);
        list.add(item2);
        Collections.sort(list, new SortByNameItemInReverseOrder());
        List<Item> expectedList = new ArrayList<>();
        expectedList.add(item4);
        expectedList.add(item3);
        expectedList.add(item2);
        expectedList.add(item1);
        assertThat(list, is(expectedList));
    }
}
