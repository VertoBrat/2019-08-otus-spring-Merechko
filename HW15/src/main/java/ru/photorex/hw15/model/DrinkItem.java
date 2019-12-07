package ru.photorex.hw15.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrinkItem extends OrderItem {

    private DrinkType type;
    private int shots = 1;
    private boolean isIced = false;

    @Override
    public String getItemName() {
        return type.name();
    }

    @Override
    public Boolean isFood() {
        return false;
    }
}
