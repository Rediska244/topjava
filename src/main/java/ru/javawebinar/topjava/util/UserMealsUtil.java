package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
//import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    static Map<LocalDate, Integer> mapDay = new HashMap<>();

    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 16, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(14, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, List<UserMealWithExceed>> mapItog = new HashMap<>();
        Map<LocalDate, List<UserMealWithExceed>> mapTrue = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            mapDay.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), (a, b) -> a + b);
            LocalTime time = userMeal.getDateTime().toLocalTime();
            // если входит
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            if (TimeUtil.isBetween(time, startTime, endTime)) {
                if (mapTrue.containsKey(localDate)) {
                    List<UserMealWithExceed> list1 = mapTrue.get(localDate);
                    list1.add(new UserMealWithExceed(
                            userMeal.getDateTime(),
                            userMeal.getDescription(),
                            userMeal.getCalories(),
                            true));
                } else {
                    List<UserMealWithExceed> list1 = new ArrayList<>();
                    list1.add(new UserMealWithExceed(
                            userMeal.getDateTime(),
                            userMeal.getDescription(),
                            userMeal.getCalories(),
                            true));
                    mapTrue.put(localDate, list1);
                }
                if (mapItog.containsKey(localDate)) {
                    if (mapDay.get(localDate) > caloriesPerDay) {
                        mapItog.remove(localDate);
                        mapItog.put(localDate, mapTrue.get(localDate));
                    } else {
                        List<UserMealWithExceed> list1 = mapItog.get(localDate);
                        list1.add(new UserMealWithExceed(
                                userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                false));
                    }
                } else {
                    if (mapDay.get(localDate) <= caloriesPerDay) {
                        List<UserMealWithExceed> list1 = new ArrayList<>();
                        list1.add(new UserMealWithExceed(
                                userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                false));
                        mapItog.put(localDate, list1);
                    }
                }
            } else {
                if (mapItog.containsKey(localDate)) {
                    if (mapDay.get(localDate) > caloriesPerDay) {
                        mapItog.remove(localDate);
                        mapItog.put(localDate, mapTrue.get(localDate));
                    }
                }
            }
        }
        List<UserMealWithExceed> list = new ArrayList<>();

        for (Map.Entry<LocalDate, List<UserMealWithExceed>> m : mapItog.entrySet()) {
            list.addAll(m.getValue());
        }
//        return values.stream().flatMap(identity()).collect(toList());
        return list;
    }
}