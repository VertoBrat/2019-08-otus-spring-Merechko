package ru.photorex.hw15;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import ru.photorex.hw15.model.*;
import ru.photorex.hw15.service.ConsoleContext;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableIntegration
@IntegrationComponentScan
@SuppressWarnings({"unchecked", "ConstantConditions"})
public class IntegrationConfig {

    private static final String ORDER_ID = "orderId";

    @Bean
    public ConsoleContext consoleContext() {
        ConsoleContext cc = new ConsoleContext();
        cc.setInputStream(System.in);
        cc.setPrintStream(System.out);
        return cc;
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(1000);
    }

    @Bean
    public QueueChannel inputOrder() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel outputOrder() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow kitchenFlow() {
        return f -> f.channel(c -> c.executor(Executors.newFixedThreadPool(2)))
                .<FoodItem, Boolean>route(FoodItem::isHotter, m -> m
                        .subFlowMapping(true, sub -> sub
                                .handle("kitchenService", "cookHotFoodItem"))
                        .subFlowMapping(false, subf -> subf
                                .handle("kitchenService", "cookColdFoodItem")))
                .transform(Message.class, m -> MessageBuilder.fromMessage(m)
                        .setHeader(ORDER_ID, ((Food) m.getPayload()).getOrderId()));
    }

    @Bean
    public IntegrationFlow barFlow() {
        return f -> f.channel(c -> c.queue(10))
                .<DrinkItem, Boolean>route(DrinkItem::isIced, m -> m
                        .subFlowMapping(true, sub -> sub
                                .handle("bartenderService", "cookColdDrink"))
                        .subFlowMapping(false, sub -> sub
                                .handle("bartenderService", "cookHotDrink")))
                .transform(Message.class, m -> MessageBuilder.fromMessage(m)
                        .setHeader(ORDER_ID, ((Drink) m.getPayload()).getOrderId()));
    }

    @Bean
    public IntegrationFlow restaurantFlow() {
        return IntegrationFlows.from("inputOrder")
                .split(Order.class, Order::getItems)
                .channel(c -> c.executor(Executors.newCachedThreadPool()))
                .<OrderItem, Boolean>route(OrderItem::isFood, mapping -> mapping
                        .subFlowMapping(true, s -> s.gateway(kitchenFlow()))
                        .subFlowMapping(false, s -> s.gateway(barFlow())))
                .aggregate(a -> a.outputProcessor(IntegrationConfig::preparedCookedOrderForDelivery)
                        .correlationStrategy(m -> m.getHeaders().get(ORDER_ID)))
                .channel("outputOrder")
                .get();
    }

    private static CookedOrder preparedCookedOrderForDelivery(MessageGroup g) {
        CookedOrder cookedOrder = new CookedOrder();
        int orderId = g.getOne().getHeaders().get(ORDER_ID, Integer.class);
        List<Food> foods = g.getMessages().stream()
                .filter(message -> message.getPayload() instanceof Food)
                .map(m -> (Food) m.getPayload())
                .collect(Collectors.toList());
        List<Drink> drinks = g.getMessages().stream()
                .filter(message -> message.getPayload() instanceof Drink)
                .map(m -> (Drink) m.getPayload())
                .collect(Collectors.toList());
        cookedOrder.setOrderId(orderId);
        cookedOrder.setDrinks(drinks);
        cookedOrder.setFoods(foods);
        return cookedOrder;
    }

    public static void sleepWithoutTryCatch(long sleepFor, TimeUnit unit) {
        boolean interrupted = false;
        try {
            unit.sleep(sleepFor);
        } catch (InterruptedException e) {
            interrupted = true;
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
