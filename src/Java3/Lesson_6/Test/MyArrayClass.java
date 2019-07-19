package Java3.Lesson_6.Test;

import java.util.Arrays;

public class MyArrayClass {

    public int[] newArrayAfterLastFour(int[] array) {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) index = i;
        }
        if (index == -1) return null;
        int newArraySize = array.length - index;
        int[] newArray = new int[newArraySize];
        newArray = Arrays.copyOfRange(array, index + 1, array.length);
        return newArray;
    }


    public boolean isOneAndFour(int[] array) {
        int four = 0;
        int one = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) one++;
            else if (array[i] == 4) four++;
            else return false;
        }
        if (one == 0 || four == 0) return false;
        return true;
    }
}
