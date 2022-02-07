package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.City;

import java.util.List;

public class CityStore {
    private StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private SessionFactory factory = new MetadataSources(registry)
            .addAnnotatedClass(City.class)
            .buildMetadata()
            .buildSessionFactory();

    public City create(City city) {
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(city);
        session.getTransaction().commit();
        session.close();
        return city;
    }

    public List<City> findAll() {
        Session session = factory.openSession();
        session.beginTransaction();
        List<City> cities = session.createQuery("from City").getResultList();
        session.getTransaction().commit();
        session.close();
        return cities;
    }
}
