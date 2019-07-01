package Java3.Lesson_1.Task_1;

public class Main {
    public static void main(String[] args) {

        String[] strings = new String[20];
        int x = 10;  // идексы элементов которые
        int y = 12;  // хотим поменьять местами

        change(strings, x, y);


    }

    public static void change(String[] strings, int indexX, int indexY) {
        String temp = strings[indexX];
        strings[indexX] = strings[indexY];
        strings[indexY] = temp;
    }
}
