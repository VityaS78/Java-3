package Java3.Lesson_5;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public abstract class Stage {
    protected int length;
    protected String description;
//    ArrayBlockingQueue<Stage> carsInTunnel = new ArrayBlockingQueue<>(2, true);
    Semaphore smp = new Semaphore(2);
    private boolean flag = true;

//    public String getDescription()
//    {
//        return description;
//    }
    public abstract String getDescription();

    public void go(Car c, int i, int l) {
        try {
            try {
//                if (i != 0) System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                if (c.getRace().getStages().get(i) instanceof Tunnel) {
                    System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                    smp.acquire();
                    System.out.println(c.getName() + " начал этап: " + description);
                    Thread.sleep(length / c.getSpeed() * 1000);
                    System.out.println(c.getName() + " закончил этап: " + description);
                    smp.release();
                } else {
                    System.out.println(c.getName() + " начал этап: " + description);
                    Thread.sleep(length / c.getSpeed() * 1000);
                    System.out.println(c.getName() + " закончил этап: " + description);
                }
                if (i == l - 1 && flag) System.out.println(c.getName() + " - WIN");
                flag = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
