package ru.job4j;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EditActionTest {
    @Test
    public void testEditAction() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        tracker.addItem(new Item("Replaced item"));
        String replacedName = "New item name";
        EditAction rep = new EditAction(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        when(input.askStr(any(String.class))).thenReturn(replacedName);
        rep.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is("==== Edit Item ====" + ln + "Item was edited" + ln));
        assertThat(tracker.findAll().get(0).getName(), is(replacedName));
    }
}