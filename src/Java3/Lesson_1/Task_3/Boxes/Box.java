package Java3.Lesson_1.Task_3.Boxes;

import Java3.Lesson_1.Task_3.Fruits.Fruit;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    ArrayList<T> list = new ArrayList<>();

    public Box() {
    }

    public void add(T t) {
        list.add(t);
    }

    public T remove() {
        if (list.isEmpty()) return null;
        T t = list.remove(0);
        return t;
    }

    public int getSize() {
        return list.size();
    }

    public float getWeigth() {
        float sum = 0;
        if (list.isEmpty()) return 0;
        for (T t : list) {
            sum += t.getWeigth();
        }
        return sum;
    }

    public boolean compare(Box<?> anotherBox) {
        return Math.abs(this.getWeigth() - anotherBox.getWeigth()) < 0.0001;
    }

    public void moveToAnotherBox(Box<T> anotherBox) {
        while (!list.isEmpty()) anotherBox.add(remove());
    }
}
