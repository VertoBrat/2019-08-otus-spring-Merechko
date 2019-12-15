package ru.photorex.hw15.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem extends OrderItem {

    private FoodType type;
    private boolean isHotter = false;

    @Override
    public String getItemName() {
        return type.name();
    }

    @Override
    public Boolean isFood() {
        return true;
    }
}
