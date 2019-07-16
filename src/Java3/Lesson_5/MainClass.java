package Java3.Lesson_5;

import java.util.concurrent.CyclicBarrier;
/*
Все синхронизировалось и правильно выводится, но кажется что что-то не так.

Насчет заданий к урокам 1 и 2, там стоит статус "Проверяется".
По базам данных в пакете Lesson_3. По 1 уроку Вы видели.
 */

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static CyclicBarrier cb;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЬЯВЛЕНИЕ >>> Подготовка!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        cb = new CyclicBarrier(CARS_COUNT);
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        while (Car.start.get() != CARS_COUNT) {
        }
        System.out.println("ВАЖНОЕ ОБЬЯВЛЕНИЕ >>> Гонка началась!!!");

        while (Car.start.get() != CARS_COUNT * 2) {
        }
        System.out.println("ВАЖНОЕ ОБЬЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}

