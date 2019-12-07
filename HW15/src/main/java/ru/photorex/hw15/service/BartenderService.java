package ru.photorex.hw15.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.photorex.hw15.model.Drink;
import ru.photorex.hw15.model.DrinkItem;

import java.util.concurrent.TimeUnit;

import static ru.photorex.hw15.IntegrationConfig.sleepWithoutTryCatch;

@Service
@RequiredArgsConstructor
public class BartenderService {

    private final IOService ioService;

    public Drink cookHotDrink(DrinkItem item) {
        return drinkMaker(1200, TimeUnit.MILLISECONDS, item);
    }

    public Drink cookColdDrink(DrinkItem item) {
        return drinkMaker(600, TimeUnit.MILLISECONDS, item);
    }

    private Drink drinkMaker(int sleep, TimeUnit unit, DrinkItem item) {
        ioService.printFormattedString("Bartender makes %s", item.getItemName());
        sleepWithoutTryCatch(sleep, unit);
        ioService.printFormattedString("Bartender MADE %s", item.getItemName());
        return new Drink(item.getOrderId(), item.getItemName());
    }
}
