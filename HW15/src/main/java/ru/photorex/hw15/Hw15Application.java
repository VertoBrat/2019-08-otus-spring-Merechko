package ru.photorex.hw15;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.photorex.hw15.model.*;
import ru.photorex.hw15.service.IOService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static ru.photorex.hw15.IntegrationConfig.sleepWithoutTryCatch;

@SpringBootApplication
public class Hw15Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Hw15Application.class, args);
        Restaurant restaurant = ctx.getBean(Restaurant.class);
        IOService ioService = ctx.getBean(IOService.class);
        while (true) {
            Order order = new Order();
            order.setId(RandomUtils.nextInt());
            order.setItems(generateOrderItems(order.getId()));
            String orderItems = order.getItems().stream()
                    .map(OrderItem::getItemName)
                    .map(String::toLowerCase)
                    .collect(Collectors.joining(", "));
            ioService.printFormattedString("New Order with number %d: %s", order.getId(), orderItems);

            CookedOrder cookedOrder = restaurant.processOrder(order);

            String foods = cookedOrder.getFoods().stream()
                    .map(Food::getName).collect(Collectors.joining(", "));
            String drinks = cookedOrder.getDrinks().stream()
                    .map(Drink::getName).collect(Collectors.joining(", "));
            ioService.printFormattedString("Delivered order with number %d and %s %s\n", cookedOrder.getOrderId(), foods, drinks);
            sleepWithoutTryCatch(500, TimeUnit.MILLISECONDS);
        }
    }

    private static Collection<OrderItem> generateOrderItems(int orderNumber) {
        List<OrderItem> items = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 10); i++) {
            items.add(generateOrderItem(orderNumber));
        }
        return items;
    }

    private static OrderItem generateOrderItem(int orderNumber) {
        return RandomUtils.nextBoolean() ? generateDrinkItem(orderNumber) : generateFoodItem(orderNumber);
    }

    private static DrinkItem generateDrinkItem(int orderNumber) {
        DrinkItem item = new DrinkItem();
        int drinkType = RandomUtils.nextInt(0, DrinkType.values().length);
        DrinkType type = DrinkType.values()[drinkType];
        item.setIced(type.getTemperature().equals("cold"));
        item.setType(type);
        item.setOrderId(orderNumber);
        return item;
    }

    private static FoodItem generateFoodItem(int orderNumber) {
        FoodItem item = new FoodItem();
        int foodType = RandomUtils.nextInt(0, FoodType.values().length);
        FoodType type = FoodType.values()[foodType];
        item.setType(type);
        item.setHotter(type.getTemperature().equals("hot"));
        item.setOrderId(orderNumber);
        return item;
    }


}
