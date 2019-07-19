package Java3.Lesson_6.Test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class TestArrayAfterLastFour {
    
    private int[] array;
    private int[] newArray;
    
    private MyArrayClass arrayClass;
    
    @Parameterized.Parameters
    public static List<int[][]> data() {
        return Arrays.asList(new int[][][]{
                {{1, 4, 2, 3, 4, 5, 6}, {5, 6}},
                {{3, 25, 4, 1, 4, 8, 20}, {8, 20}},
                {{1, 8, 4}, {0}}
        });
    }
    
    public TestArrayAfterLastFour(int[] array, int[] newArray) {
        this.array = array;
        this.newArray = newArray;
    }
    
    @Before
    public void startTest() {
        arrayClass = new MyArrayClass();
    }
    
    @Test
    public void massTestArrayLastFour() {
        Assert.assertArrayEquals(newArray, arrayClass.newArrayAfterLastFour(array));
    }

    @After
    public void finish() {
        arrayClass = null;
    }
}
