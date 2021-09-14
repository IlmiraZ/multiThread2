package ru.ilmira;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MainClass {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        CountDownLatch prep = new CountDownLatch(CARS_COUNT);
        CyclicBarrier start = new CyclicBarrier(CARS_COUNT);
        CountDownLatch finish = new CountDownLatch(CARS_COUNT);

        Race race = new Race(new Road(60), new Tunnel(CARS_COUNT / 2), new Road(40));

        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), prep, start, finish);
        }
        for (Car car : cars) {
            new Thread(car).start();
        }
        prep.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        finish.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
