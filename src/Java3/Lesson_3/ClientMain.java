package Java3.Lesson_3;

import Java3.Lesson_3.Client.ClientWindow;

public class ClientMain {
    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 8080;
    public static void main(String[] args) {
//        new ClientWindow(SERVER_HOST, SERVER_PORT);
        new ClientWindow(SERVER_HOST, SERVER_PORT);
    }
}
