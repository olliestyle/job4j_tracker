package tracker;

import org.junit.Test;
import ru.job4j.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindByIDActionTest {
    @Test
    public void testFindByIdActionTest() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        tracker.add(new Item("FoundById item"));
        FindByIDAction find = new FindByIDAction(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        when(input.askStr(any())).thenReturn("1");
        find.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is("==== Find Item by ID ====" + ln
                + "Item was found: id " + 1 + ", name " + "FoundById item" + ln));
        assertThat(tracker.findAll().get(0).getName(), is("FoundById item"));
    }
}