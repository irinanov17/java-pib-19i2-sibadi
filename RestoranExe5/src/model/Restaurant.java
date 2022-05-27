package model;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Restaurant {
    public Menu menu;
    public List<Table> tables;
    public List<OrderDishes> orders;

    public static void main(String[] args) throws IOException {
        Restaurant rest = new Restaurant();

        File file = new File("data.txt");
        if (!file.exists()) {
            System.out.println("Файл отсутствует, генерируем информацию...");
            rest.init(rest);
            Gson gson = new Gson();
            Files.write( Paths.get("data.txt"), gson.toJson(rest).getBytes());
        } else {
            System.out.println("Читаем файл, извлекаем данные");
            Gson gson = new Gson();
            String text = new String(Files.readAllBytes(Paths.get("data.txt")), StandardCharsets.UTF_8);
            rest = gson.fromJson(text, Restaurant.class);
        }


        System.out.println("Меню ресторана:");
        rest.printMenu();

        System.out.println("\n\nСтолики ресторана:");
        rest.printTables();
        System.out.println("\n\nПоступил заказ...");


        System.out.println("\n\nПоступил заказ...");


        System.out.println();
        System.out.println();

        System.out.println("\nСписок заказов:");
        for (Order order : rest.orders) {
            System.out.println(order.toString());
        }

        System.out.println("\n\nЗарезервированы столики 1, 3...");

        System.out.println("\nКто-то пытается зарезервировать столик 1...");


        System.out.println("\n\nЗаняты столик 5 и 7");

        System.out.println("\n\nСписок столов: ");
        rest.printTables();
    }

    Restaurant() {
        menu = new Menu();
        tables = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public void init(Restaurant rest) {
        Collections.addAll(rest.menu.dishes, new Dish("Курица", 500),
                new Dish("Утка", 450),
                new Dish("Борщ", 250),
                new Dish("Лосось", 4000),
                new Dish("Компот", 250),
                new Dish("Кусок пирога", 200),
                new Dish("Пицца", 550));

        for (int i = 0; i < 7; i++) {
            rest.tables.add(new Table(TableStatus.FREE));
        }

        OrderDishes orderDishes = new OrderDishes(OrderStatus.PREPARE);
        rest.addDishInOrder(orderDishes, 2);
        rest.addDishInOrder(orderDishes, 5);

        OrderDishes orderDishes2 = new OrderDishes(OrderStatus.PREPARE);
        rest.addDishInOrder(orderDishes2, 3);
        rest.addDishInOrder(orderDishes2, 6);

        rest.makeOrder(orderDishes);
        rest.makeOrder(orderDishes2);

        rest.bookTable(1);
        rest.bookTable(3);
        rest.bookTable(1);

        rest.buyTable(5);
        rest.buyTable(7);
    }

    public void printTables() {
        for (int i = 0; i < tables.size(); i++) {
            System.out.println((i+1) +": " + tables.get(i).toString());
        }
    }

    public void bookTable(int index) {
        if (index <= 0 || index > tables.size()) {
            System.out.println("Столика не существует");
            return;
        }
        if (tables.get(index - 1).tableStatus != TableStatus.BUYS && tables.get(index - 1).tableStatus != TableStatus.RESERVED) {
            tables.get(index - 1).tableStatus = TableStatus.RESERVED;
            System.out.println("Столик забронирован");
        } else {
            System.out.println("Столик не свободен");
        }
    }

    public void buyTable(int index) {
        if (index <= 0 || index > tables.size()) {
            System.out.println("Столика не существует");
            return;
        }
        if (tables.get(index - 1).tableStatus != TableStatus.BUYS && tables.get(index - 1).tableStatus != TableStatus.RESERVED) {
            tables.get(index - 1).tableStatus = TableStatus.BUYS;
            System.out.println("Столик успешно занят");
        } else {
            System.out.println("Столик не свободен");
        }
    }

    public void printMenu() {
        for (Dish dish : menu.dishes) {
            System.out.println(dish.toString());
        }
    }

    public void addDishInOrder(OrderDishes orderDishes, int dishIndex) {
        if (dishIndex < 0 || dishIndex >= menu.dishes.size()) {
            System.out.println("Блюда не существует");
            return;
        }
        orderDishes.dishes.add(menu.dishes.get(dishIndex));
        System.out.println("В заказ добавлено блюдо " + menu.dishes.get(dishIndex - 1));
    }

    public void makeOrder(OrderDishes orderDishes) {
        orderDishes.pay();
        orders.add(orderDishes);
        System.out.println("Заказ успешно оплачен и принят в работу");
    }

}
