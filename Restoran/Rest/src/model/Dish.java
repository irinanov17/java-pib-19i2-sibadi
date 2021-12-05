package model;

public class Dish {
    public String name;
    public double price;

    public Dish(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return this.name + ", цена: " + String.format("%.2f", this.price) + "p";
    }
}
