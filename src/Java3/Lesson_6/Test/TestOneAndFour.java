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
public class TestOneAndFour {

    private MyArrayClass arrayClass;
    private int[] array;
    boolean bo;

    @Parameterized.Parameters
    public static List<Object[]> data() {
        int[] ar1 = {1, 4, 1, 1, 4, 4, 4};
        int[] ar2 = {1, 1, 4, 1, 4, 1, 1};
        int[] ar3 = {1, 8, 4};
        return Arrays.asList(new Object[][]{
                {ar1, true},
                {ar2, true},
                {ar3, false}
        });
    }

    public TestOneAndFour(int[] array, boolean bo) {
        this.array = array;
        this.bo = bo;
    }

    @Before
    public void startTest() {
        arrayClass = new MyArrayClass();
    }

    @Test
    public void massTestOneAndFour() {
        Assert.assertEquals(bo, arrayClass.isOneAndFour(array));
    }

    @After
    public void finish() {
        arrayClass = null;
    }
}
