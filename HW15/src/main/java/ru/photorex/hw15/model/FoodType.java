package ru.photorex.hw15.model;

public enum FoodType {

    MEAT("hot"), SALAD("cold"), BREAD("cold"), FISH("hot"), CHICKEN("hot"), SAUCE("cold");

    private final String temperature;

    private FoodType(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperature() {
        return temperature;
    }
}
