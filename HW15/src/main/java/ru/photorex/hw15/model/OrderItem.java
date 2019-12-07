package ru.photorex.hw15.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class OrderItem {

    private int orderId;

    public abstract String getItemName();

    public abstract Boolean isFood();
}
