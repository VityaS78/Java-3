package Java3.Lesson_3.Server;

import java.util.ArrayList;

public class Filter {
    private static ArrayList<String> censuredList = new ArrayList<>();

    public Filter() {
        censuredList.add("Python");
        censuredList.add("C++");
        censuredList.add("Pascal");
        censuredList.add("PYTHON");
        censuredList.add("c++");
        censuredList.add("PASCAL");
        censuredList.add("python");
        censuredList.add("pascal");
    }

    public String filter(String msg) {
        for (String s: censuredList) {
            msg = msg.replace(s, "\"цензура\"");
        }
        return msg;
    }

}

