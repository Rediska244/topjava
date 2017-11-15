package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Map;

public interface MealRepository {
    Meal save(int user, Meal meal);

    void delete(int user, int id);

    Meal get(int user, int id);

    Collection<Meal> getAll(int user);
}
