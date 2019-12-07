package ru.photorex.hw15.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CookedOrder {

    private int orderId;
    private List<Drink> drinks;
    private List<Food> foods;
}
