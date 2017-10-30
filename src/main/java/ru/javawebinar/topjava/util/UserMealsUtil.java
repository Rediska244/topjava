package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
//import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        System.out.println("TODO return filtered list with correctly exceeded field");
        List<UserMealWithExceed> list = new ArrayList<>();
        Map<LocalDate, Integer> mapDay = new HashMap<>();
        for( UserMeal userMeal: mealList){
            // заносим в таблицу дней
            int calories = userMeal.getCalories();
            if( mapDay.containsKey(userMeal.getDateTime().toLocalDate()))
                calories += mapDay.get(userMeal.getDateTime().toLocalDate());
            mapDay.put(userMeal.getDateTime().toLocalDate(), calories);
        }
        for( UserMeal userMeal: mealList){
            // заносим в итоговую таблицу, если время в интервале
            LocalTime time = userMeal.getDateTime().toLocalTime();
            if(TimeUtil.isBetween(time, startTime, endTime)){
                list.add(new UserMealWithExceed(
                        userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        mapDay.get(userMeal.getDateTime().toLocalDate()) <= caloriesPerDay));
            }
        }

        return list;
    }
}
