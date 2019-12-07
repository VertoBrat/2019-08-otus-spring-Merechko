package ru.photorex.hw15;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.photorex.hw15.model.CookedOrder;
import ru.photorex.hw15.model.Order;

@MessagingGateway
public interface Restaurant {

    @Gateway(requestChannel = "inputOrder", replyChannel = "outputOrder")
    CookedOrder processOrder(Order order);
}
