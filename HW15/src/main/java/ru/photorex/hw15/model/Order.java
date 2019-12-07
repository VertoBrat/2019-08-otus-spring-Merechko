package ru.photorex.hw15.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private int id;
    private Collection<OrderItem> items;

    public void addItem(OrderItem item) {
        items.add(item);
    }
}
