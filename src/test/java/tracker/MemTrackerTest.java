package tracker;

import ru.job4j.Item;
import ru.job4j.MemTracker;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;


public class MemTrackerTest {
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("test1");
        memTracker.add(item);
        Item result = memTracker.findById(item.getId());
        assertThat(result.getName(), is(item.getName()));
    }

    @Test
    public void whenNotFindById() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("test1");
        memTracker.add(item);
        Item result = memTracker.findById("1874619823749");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void whenReplace() {
        MemTracker memTracker = new MemTracker();
        Item bug = new Item("Bug");
        memTracker.add(bug);
        String id = bug.getId();
        Item bugWithDesc = new Item("Bug with description");
        memTracker.replace(id, bugWithDesc);
        assertThat(memTracker.findById(id).getName(), is("Bug with description"));
    }

    @Test
    public void whenReplaceNull() {
        MemTracker memTracker = new MemTracker();
        Item bugWithDesc = new Item("Bug with description");
        memTracker.replace("5648941561", bugWithDesc);
        assertThat(memTracker.replace("5648941561", bugWithDesc), is(false));
    }

    @Test
    public void whenDelete() {
        MemTracker memTracker = new MemTracker();
        Item bug = new Item("Bug");
        memTracker.add(bug);
        String id = bug.getId();
        memTracker.delete(id);
        assertThat(memTracker.findById(id), is(nullValue()));
    }

    @Test
    public void whenDeleteNothing() {
        MemTracker memTracker = new MemTracker();
        assertThat(memTracker.delete("5498131564"), is(false));
    }
}
