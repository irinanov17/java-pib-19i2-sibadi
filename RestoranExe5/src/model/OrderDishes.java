package model;

import java.util.ArrayList;
import java.util.List;

public class OrderDishes implements Order{

    public List<Dish> dishes;
    public OrderStatus orderStatus;

    public OrderDishes(List<Dish> dishes, OrderStatus orderStatus) {
        this.dishes = dishes;
        this.orderStatus = orderStatus;
    }

    public OrderDishes(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        dishes = new ArrayList<>();
    }

    @Override
    public double getAmount() {
        double amount = 0;
        for (Dish dish : dishes) {
            amount += dish.price;
        }
        return amount;
    }

    @Override
    public void pay() {
        orderStatus = OrderStatus.IN_PROGRESS;
    }

    @Override
    public String toString() {
        String status = "";

        switch (orderStatus) {
            case PREPARE:
                status = "Открыт";
                break;
            case IN_PROGRESS:
                status = "Выполняется";
                break;
            case FAILED:
                status = "Не выполнен";
                break;
            case CANCELED:
                status = "Отменён";
                break;
            case EXECUTED:
                status = "Выполнен";
                break;
        }

        String dishesString = "";
        for (Dish dish : this.dishes) {
            dishesString += dish.toString() + " ";
        }

        return "Заказ на сумму " + getAmount() + "p, статус: " + status + ", блюда: " + dishesString;
    }
}
