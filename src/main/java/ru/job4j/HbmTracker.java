package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Random;

public class HbmTracker implements Store {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item addItem(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        String query = "update Item set name = :name, description = :description, created = :created where id = :id";
        Session session = sf.openSession();
        session.beginTransaction();
        int updated = session.createQuery(query)
                    .setParameter("name", item.getName())
                    .setParameter("description", item.getDescription())
                    .setParameter("created", item.getCreated())
                    .setParameter("id", id)
                    .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return updated > 0;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "delete Item where id = :id";
        Session session = sf.openSession();
        session.beginTransaction();
        int updated = session.createQuery(query).setParameter("id", id).executeUpdate();
        session.getTransaction().commit();
        session.close();
        return updated > 0;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> allItems = session.createQuery("select item from Item item", Item.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return allItems;
    }

    @Override
    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> allItemsByName = session.createQuery("from Item i where i.name = :name", Item.class)
                .setParameter("name", key)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return allItemsByName;
    }

    @Override
    public Item findById(Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public static void main(String[] args) {
        HbmTracker hbmTracker = new HbmTracker();
        System.out.println("findAll method");
        System.out.println("----------------------------------------------");
        System.out.println(hbmTracker.findAll());
        Item added = hbmTracker.addItem(Item.of(
                String.valueOf(new Random().nextInt(300)),
                String.valueOf(new Random().nextInt(300)),
                Timestamp.from(Instant.now())));
        System.out.println("findAll method after add one item");
        System.out.println("----------------------------------------------");
        System.out.println(hbmTracker.findAll());
        System.out.println("replace method");
        System.out.println("----------------------------------------------");
        System.out.println("replace is " + hbmTracker.replace(added.getId(), Item.of(
                "replaceName",
                "replaceDesc",
                Timestamp.from(Instant.now()))));
        System.out.println("findAll method after replace");
        System.out.println("----------------------------------------------");
        System.out.println(hbmTracker.findAll());
        System.out.println("findById method");
        System.out.println("----------------------------------------------");
        System.out.println(hbmTracker.findById(added.getId()));
        System.out.println("delete method");
        System.out.println("----------------------------------------------");
        System.out.println("delete is " + hbmTracker.delete(added.getId()));
        System.out.println("findAll method after delete");
        System.out.println("----------------------------------------------");
        System.out.println(hbmTracker.findAll());
        System.out.println("findByName method");
        System.out.println("----------------------------------------------");
        System.out.println(hbmTracker.findByName("item2"));
    }
}
