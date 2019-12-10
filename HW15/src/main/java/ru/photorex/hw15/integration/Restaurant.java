package ru.photorex.hw15.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.photorex.hw15.model.CookedOrder;
import ru.photorex.hw15.model.Order;

@MessagingGateway
public interface Restaurant {

    @Gateway(requestChannel = "restaurantFlow.input")
    CookedOrder processOrder(Order order);
}
