package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Override
    public Meal save(int user, Meal meal) {
        return null;
    }

    @Override
    public void delete(int user, int id) {

    }

    @Override
    public Meal get(int user, int id) {
        return null;
    }

    @Override
    public Collection<Meal> getAll(int user) {
        return null;
    }
}