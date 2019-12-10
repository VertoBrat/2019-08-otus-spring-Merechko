package ru.photorex.hw15.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.photorex.hw15.model.Food;
import ru.photorex.hw15.model.FoodItem;

import java.util.concurrent.TimeUnit;

import static ru.photorex.hw15.utils.SafetySleepHelper.sleepWithoutTryCatch;

@Service
@RequiredArgsConstructor
public class KitchenService {

    private final IOService ioService;

    public Food cookHotFoodItem(FoodItem item) {
        return foodMaker(3, TimeUnit.SECONDS, item);
    }

    public Food cookColdFoodItem(FoodItem item) {
        return foodMaker(1, TimeUnit.SECONDS, item);
    }

    private Food foodMaker(int sleep, TimeUnit unit, FoodItem item) {
        ioService.printFormattedString("Cooking %s", item.getItemName());
        sleepWithoutTryCatch(sleep, unit);
        ioService.printFormattedString("Cooking %s DONE", item.getItemName());
        return new Food(item.getOrderId(), item.getItemName());
    }
}
