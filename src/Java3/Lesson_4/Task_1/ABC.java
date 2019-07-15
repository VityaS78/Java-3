package Java3.Lesson_4.Task_1;


/*
Задание по многопоточности лежит в пакете Lesson_3, по базам данных там же.
 */
public class ABC {
    String state;

    synchronized void printA(boolean running) {
        if (!running) {
            state = "A";
            notify();
            return;
        }

        System.out.print("A");
        state = "A";
        notify();
        try {
            while (!state.equals("C")) wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void printB(boolean running) {
        if (!running) {
            state = "B";
            notify();
            return;
        }

        System.out.print("B");
        state = "B";
        notify();
        try {
            while (!state.equals("A")) wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void printC(boolean running) {
        if (!running) {
            state = "C";
            notify();
            return;
        }

        System.out.print("C ");
        state = "C";
        notifyAll();
        try {
            while (!state.equals("B")) wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyThread implements Runnable {
        Thread thr;
        ABC abc;

        MyThread(String name, ABC abc1) {
            thr = new Thread(this, name);
            abc = abc1;
            thr.start();
        }

        @Override
        public void run() {
            if (thr.getName().compareTo("A") == 0) {
                for (int i = 0; i < 5; i++) abc.printA(true);
                abc.printA(false);
            } else if (thr.getName().compareTo("B") == 0) {
                for (int i = 0; i < 5; i++) abc.printB(true);
                abc.printB(false);
            } else {
                for (int i = 0; i < 5; i++) abc.printC(true);
                abc.printC(false);
            }

        }
    }

    static class ThreadCom {
        public static void main(String[] args) {
            ABC abc1 = new ABC();
            MyThread mt1 = new MyThread("A", abc1);
            MyThread mt2 = new MyThread("B", abc1);
            MyThread mt3 = new MyThread("C", abc1);

            try {
                mt1.thr.join();
                mt2.thr.join();
                mt3.thr.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



//    private final Object obj = new Object();
//    private volatile boolean currentA = true;
//    private volatile boolean currentB = false;
//    private volatile boolean currentC = false;
//
//    public static void main(String[] args) {
//        ABC abc = new ABC();
//        Thread thr1 = new Thread(() -> {
//            abc.printA();
//        });
//        Thread thr2 = new Thread(() -> {
//            abc.printB();
//        });
//        Thread thr3 = new Thread(() -> {
//            abc.printC();
//        });
//
//        thr1.start();
//        thr2.start();
//        thr3.start();
//    }
//
//    public void printA() {
//        synchronized (obj) {
//            try {
//                for (int i = 0; i < 5; i++) {
//                    while (!currentA) {
//                        obj.wait();
//                    }
//                    System.out.print("A");
//                    currentA = false;
//                    currentB = true;
//                    obj.notify();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void printB() {
//        synchronized (obj) {
//            try {
//                for (int i = 0; i < 5; i++) {
//                    while (!currentB) {
//                        obj.wait();
//                    }
//                    System.out.print("B");
//                    currentB = false;
//                    currentC = true;
//                    obj.notify();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void printC() {
//        synchronized (obj) {
//            try {
//                for (int i = 0; i < 5; i++) {
//                    while (!currentC) {
//                        obj.wait();
//                    }
//                    System.out.println("C");
//                    currentC = false;
//                    currentA = true;
//                    obj.notifyAll();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
