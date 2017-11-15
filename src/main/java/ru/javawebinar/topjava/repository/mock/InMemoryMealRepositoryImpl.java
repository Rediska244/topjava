package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.ADMIN_ID;
import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.USER_ID;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public static final Comparator<Meal> USER_MEAL_DATE_COMP = (u1, u2) -> u1.getDateTime().compareTo(u2.getDateTime());

    {
        MealsUtil.MEALS.forEach(um -> save(USER_ID, um));

        save(ADMIN_ID, new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510));
        save(ADMIN_ID, new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500));
    }

    @Override
    public Meal save(int user, Meal meal) {
        Map<Integer, Meal> list = repository.get(user);
        if (list == null) meal.setId(counter.incrementAndGet());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        list.put(meal.getId(), meal);
        repository.put(user, list);
        return meal;
    }

    @Override
    public void delete(int user, int id) {
        Map<Integer, Meal> list = repository.get(user);
        list.remove(id);
        if (list != null) repository.put(user, list);
        else
            repository.remove(user);
    }

    @Override
    public Meal get(int user, int id) {
//        return repository.get(id);
        Map<Integer, Meal> list = repository.get(user);
        if (list == null) return null;
        return list.get(id);
    }

    @Override
    public Collection<Meal> getAll(int user) {
        if (repository.get(user) == null) return null;
        return repository.get(user).values().stream().sorted(USER_MEAL_DATE_COMP).collect(Collectors.toList());
    }
}

