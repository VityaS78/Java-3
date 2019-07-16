package Java3.Lesson_5;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Runnable {
    static AtomicInteger start = new AtomicInteger(0);
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
//    ArrayBlockingQueue<Stage> carsInTunnel = new ArrayBlockingQueue<>(2, true);

    //    public static int getStart() {
//        return start;
//    }
    public Race getRace() {
        return race;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + "готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + "готов");
//            start.getAndAdd(1);
            MainClass.cb.await();
            start.getAndAdd(1);
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this, i, race.getStages().size());
        }
        try {
            MainClass.cb.await();
            start.getAndAdd(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
