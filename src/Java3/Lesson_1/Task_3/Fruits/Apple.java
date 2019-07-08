package Java3.Lesson_1.Task_3.Fruits;

public class Apple extends Fruit{

    public Apple(float weigth) {
        super(weigth);
    }
//

    @Override
    public float getWeigth() {
        return this.weigth;
    }
}
