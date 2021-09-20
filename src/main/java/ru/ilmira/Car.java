package ru.ilmira;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT = 0;
    private static boolean winner;

    private final Race race;
    private final int speed;
    private final String name;

    private final CountDownLatch prep;
    private final CyclicBarrier start;
    private final CountDownLatch finish;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CountDownLatch prep, CyclicBarrier start, CountDownLatch finish) {
        this.race = race;
        this.speed = speed;
        this.prep = prep;
        this.start = start;
        this.finish = finish;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            prep.countDown();
            System.out.println(this.name + " готов");
            start.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        findWinner(this);
        finish.countDown();
    }

    private static synchronized void findWinner(Car c) {
        if (!winner) {
            winner = true;
            System.out.println(c.name + " - WIN");
        }
    }
}
