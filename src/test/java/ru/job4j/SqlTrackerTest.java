package ru.job4j;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SqlTrackerTest {

    public Connection init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void createItem() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item toAdd = new Item("hello");
            tracker.addItem(toAdd);
            assertThat(tracker.findByName("hello").size(), is(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindAll() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.addItem(new Item("aaa"));
            tracker.addItem(new Item("bbb"));
            assertThat(tracker.findAll().size(), is(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenEditIsGood() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item toAdd = new Item("aaa");
            tracker.addItem(toAdd);
            Item rep = new Item("bbb");
            tracker.replace(toAdd.getId(), rep);
            assertThat(tracker.findById(toAdd.getId()).getName(), is("bbb"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenDeleteIsGood() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item toDelete = new Item("aaa");
            tracker.addItem(toDelete);
            tracker.delete(toDelete.getId());
            assertThat(tracker.findAll().size(), is(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindByIdIsGood() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item toAdd = new Item("aaa");
            tracker.addItem(toAdd);
            assertEquals(tracker.findById(toAdd.getId()), toAdd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindByNameActionisGood() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.addItem(new Item("aaa"));
            tracker.addItem(new Item("aaa"));
            assertEquals(tracker.findByName("aaa").size(), 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}