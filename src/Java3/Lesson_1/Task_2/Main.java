package Java3.Lesson_1.Task_2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int[] nums = new int[10];
        for (int i = 0; i < nums.length; i++) {
            // заполняем массив
        }

        ArrayList<Integer> list = toArrayList(nums);
    }

    public static ArrayList toArrayList(int[] nums) {
        ArrayList list = new ArrayList();
        for (int i = 0; i < nums.length; i++) {
            list.add(nums[i]);
        }
        nums = null;
        return list;
    }
}
