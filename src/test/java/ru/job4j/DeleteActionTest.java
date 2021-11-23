package ru.job4j;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteActionTest {
    @Test
    public void testDeleteAction() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        tracker.addItem(new Item("Deleted item"));
        DeleteAction del = new DeleteAction(out);
        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn("1");
        del.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is("==== Delete Item ====" + ln + "Item was deleted" + ln));
        assertTrue(tracker.findAll().isEmpty());
    }

}