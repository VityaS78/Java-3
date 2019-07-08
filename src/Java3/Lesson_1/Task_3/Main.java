package Java3.Lesson_1.Task_3;

import Java3.Lesson_1.Task_3.Boxes.Box;
import Java3.Lesson_1.Task_3.Fruits.Apple;
import Java3.Lesson_1.Task_3.Fruits.Orange;

public class Main {
    public static void main(String[] args) {
        Box<Apple> appleBox = new Box<>();

        // если попробуем так, то IDEA ругается
        // appleBox.add(new Orange(1.0f));

        Box<Orange> orangeBox = new Box<>();
        int n = 10;
        int m = 9;

        for (int i = 0; i < n; i++) {
            appleBox.add(new Apple(1.1f));
        }

        for (int i = 0; i < m; i++) {
            orangeBox.add(new Orange(1.2f));
        }

        System.out.println(appleBox.compare(orangeBox) ? "Коробки весят одинаково" : "Коробки разного веса");

        Box<Apple> appleBox1 = new Box<>();
        for (int i = 0; i < n; i++) {
            appleBox1.add(new Apple(1.1f));
        }

        System.out.println(appleBox.compare(appleBox1));

        System.out.println(appleBox1.getWeigth());
        System.out.println(appleBox.getWeigth());

        appleBox.moveToAnotherBox(appleBox1);

        System.out.println(appleBox1.getWeigth());
        System.out.println(appleBox.getWeigth());
    }
}
