package Java3.Lesson_5;

public class Road extends Stage {

    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public String getDescription() {
        return description;
    }

//    @Override
//    public void go(Car c) {
//        try {
//            try {
//                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
//                System.out.println(c.getName() + " начал этап: " + description);
//                Thread.sleep(length / c.getSpeed() * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                System.out.println(c.getName() + " закончил этап: " + description);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

