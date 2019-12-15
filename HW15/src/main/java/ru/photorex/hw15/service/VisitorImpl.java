package ru.photorex.hw15.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.photorex.hw15.integration.Restaurant;
import ru.photorex.hw15.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitorImpl implements Visitor {

    private final Restaurant restaurant;
    private final IOService ioService;

    @Override
    @Scheduled(fixedDelayString = "${fixedDelay.in.millis}", initialDelay = 1000)
    public void makeOrder() {
        Order order = generateOrder();
        printNewOrderInformation(order);
        CookedOrder cookedOrder = cooking(order);
        printCookedOrderInformation(cookedOrder);
    }

    private CookedOrder cooking(Order order) {
        return restaurant.processOrder(order);
    }

    private Order generateOrder() {
        Order order = new Order();
        order.setId(RandomUtils.nextInt());
        order.setItems(generateOrderItems(order.getId()));
        return order;
    }

    private Collection<OrderItem> generateOrderItems(int orderNumber) {
        List<OrderItem> items = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 10); i++) {
            items.add(generateOrderItem(orderNumber));
        }
        return items;
    }

    private OrderItem generateOrderItem(int orderNumber) {
        return RandomUtils.nextBoolean() ? generateDrinkItem(orderNumber) : generateFoodItem(orderNumber);
    }

    private DrinkItem generateDrinkItem(int orderNumber) {
        DrinkItem item = new DrinkItem();
        int drinkType = RandomUtils.nextInt(0, DrinkType.values().length);
        DrinkType type = DrinkType.values()[drinkType];
        item.setIced(type.getTemperature().equals("cold"));
        item.setType(type);
        item.setOrderId(orderNumber);
        return item;
    }

    private FoodItem generateFoodItem(int orderNumber) {
        FoodItem item = new FoodItem();
        int foodType = RandomUtils.nextInt(0, FoodType.values().length);
        FoodType type = FoodType.values()[foodType];
        item.setType(type);
        item.setHotter(type.getTemperature().equals("hot"));
        item.setOrderId(orderNumber);
        return item;
    }

    private void printNewOrderInformation(Order order) {
        String orderItems = order.getItems().stream()
                .map(OrderItem::getItemName)
                .map(String::toLowerCase)
                .collect(Collectors.joining(", "));
        ioService.printFormattedString("New Order with number %d: %s", order.getId(), orderItems);
    }

    private void printCookedOrderInformation(CookedOrder cookedOrder) {
        String foods = cookedOrder.getFoods().stream()
                .map(Food::getName).collect(Collectors.joining(", "));
        String drinks = cookedOrder.getDrinks().stream()
                .map(Drink::getName).collect(Collectors.joining(", "));
        ioService.printFormattedString("Delivered order with number %d and %s %s\n", cookedOrder.getOrderId(), foods, drinks);
    }
}
