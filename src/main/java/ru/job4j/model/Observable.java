package ru.job4j.model;

public interface Observable<T> {
    void notifyObservers(T obj);
}
