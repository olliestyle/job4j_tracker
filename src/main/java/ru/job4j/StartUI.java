package ru.job4j;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StartUI {

    private final Output out;

    public StartUI(Output out) {
        this.out = out;
    }

    public void init(Input input, Store store, List<UserAction> actions) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select: ", actions.size());
            UserAction action = actions.get(select);
            run = action.execute(input, store);
        }
    }

        private void showMenu(List<UserAction> actions) {
        System.out.println("Menu.");
        int index = 0;
        for (UserAction action: actions) {
            System.out.println(index + " " + action.name());
            index++;
        }
    }

    public static void main(String[] args) {
        Input input = new ConsoleInput();
        Output output = new ConsoleOutput();
        Input validate = new ValidateInput(input);
        Connection connection;
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        }  catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try (Store tracker = new SqlTracker(connection)) {
            List<UserAction> actions = new ArrayList<>();
            actions.add(new CreateAction(output));
            actions.add(new ShowAllAction());
            actions.add(new EditAction(output));
            actions.add(new DeleteAction(output));
            actions.add(new FindByIDAction(output));
            actions.add(new FindByNameAction(output));
            actions.add(new ExitAction());
            new StartUI(output).init(validate, tracker, actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
