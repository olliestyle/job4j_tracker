package tracker;

import org.junit.Test;
import ru.job4j.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindByNameActionTest {
    @Test
    public void whenCheckOutput() {
        Output output = new ConsoleOutput();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream def = System.out;
        System.setOut(new PrintStream(out));
        Store memTracker = new MemTracker();
        Item item = new Item("Hello");
        memTracker.addItem(item);
        FindByNameAction action = new FindByNameAction(output);
        action.execute(new StubInput(new String[]{item.getName()}), memTracker);
        String expect = "==== Find Item by Name ====" + System.lineSeparator() + "List of items that was found: " + new StringJoiner("")
                .add(System.lineSeparator())
                .add("" + item.getId() + " " + item.getName())
                .add(System.lineSeparator())
                .toString();
        assertThat(new String(out.toByteArray()), is(expect));
        System.setOut(def);
    }

    @Test
    public void testFindByIdActionTest() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        tracker.addItem(new Item("FoundByName item"));
        FindByNameAction find = new FindByNameAction(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        when(input.askStr(any())).thenReturn("FoundByName item");
        find.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is("==== Find Item by Name ====" + ln
                + "List of items that was found: " + ln + "1 FoundByName item" + ln));
        assertThat(tracker.findAll().get(0).getName(), is("FoundByName item"));
    }
}
