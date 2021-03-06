package ru.job4j;

import ru.job4j.model.Observable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Для выполнения запроса PreparedStatement имеет три метода:
 * 1. boolean execute(): выполняет любую SQL-команду
 * 2. ResultSet executeQuery(): выполняет команду SELECT,
 *    которая возвращает данные в виде ResultSet
 * 3. int executeUpdate(): выполняет такие SQL-команды,
 *    как INSERT, UPDATE, DELETE, CREATE и возвращает количество измененных строк
 */

public class SqlTracker implements Store {

    private final Connection connection;

    public SqlTracker(Connection connection) {
        this.connection = connection;
    }

    public void init() {
         try {
             DatabaseMetaData metaData = connection.getMetaData();
             System.out.println(metaData.getUserName());
             System.out.println(metaData.getURL());
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }

    @Override
    public Item addItem(Item item) {
        String sqlAdd = "insert into items (name) values (?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlAdd, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getName());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    item.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    private int indexOf(int id) {
        int result = -1;
        String sqlFindId = "select * from items where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlFindId)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        int rows = 0;
        int idToReplace = indexOf(id);
        String sqlEdit = "update items set name = ? where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlEdit)) {
            ps.setString(1, item.getName());
            ps.setInt(2, idToReplace);
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows != 0;
    }

    @Override
    public boolean delete(Integer id) {
        int rows = 0;
        int idToDelete = indexOf(id);
        String sqlDelete = "delete from items where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlDelete)) {
            ps.setInt(1, idToDelete);
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows != 0;
    }

    @Override
    public List<Item> findAll() {
        List<Item> result = new ArrayList<>();
        String sqlFindAll = "select * from items";
        try (PreparedStatement ps = connection.prepareStatement(sqlFindAll)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Thread.sleep(1000);
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                result.add(item);
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void findAllByReact(Observable<Item> observable) {
        String sqlFindAll = "select * from items";
        try (PreparedStatement ps = connection.prepareStatement(sqlFindAll)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Thread.sleep(1000);
                observable.notifyObservers(
                        Item.of(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getTimestamp("created"))
                );
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        String sqlFindByName = "select * from items where name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlFindByName)) {
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Item findById(Integer id) {
        Item itemToFind = null;
        int idToFind = indexOf(id);
            String sqlEdit = "select * from items where id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sqlEdit)) {
                ps.setInt(1, idToFind);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    itemToFind = new Item();
                    itemToFind.setId(rs.getInt("id"));
                    itemToFind.setName(rs.getString("name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return itemToFind;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
