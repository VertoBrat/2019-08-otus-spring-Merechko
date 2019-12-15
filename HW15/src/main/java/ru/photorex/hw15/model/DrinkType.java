package ru.photorex.hw15.model;

public enum DrinkType {

    TEA("hot"), COFFEE("hot"), JUICE("cold"), PEPSI("cold"), VODKA("cold"), WHISKEY("cold");

    private final String temperature;

    private DrinkType(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperature() {
        return temperature;
    }
}
