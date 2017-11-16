package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealService {
    Meal save(int user, Meal meal);

    void delete(int user, int id);

    Meal get(int user, int id);

    Collection<Meal> getAll(int user);
}