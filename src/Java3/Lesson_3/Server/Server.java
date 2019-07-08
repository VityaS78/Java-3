package Java3.Lesson_3.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final int PORT = 8080;
    private List<ClientHandler> clients;
    private Filter filter;


    public Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            clients = new ArrayList<>();
            SQLAuth.connect();
            filter = new Filter();
            while (true) {
                System.out.println("Ожидаем подключения клиента");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            System.out.println("Ошибка сервера");
        } finally {
        }
    }

    public synchronized void sendMsgToAll(String strFromClient) {
        for (ClientHandler cl : clients) {
            cl.sendMessege(strFromClient);
        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler cl:clients) {
            if(nick.equalsIgnoreCase(cl.getName())) return true;
        }
        return false;
    }

    public synchronized void addClient(ClientHandler cl) {
        clients.add(cl);
    }

    public synchronized void removeClient(ClientHandler cl) {
        clients.remove(cl);
    }

    public synchronized void distributeMsg(String msgFromClient, String name) {
        System.out.println(msgFromClient);
        if (msgFromClient.startsWith("/ToNick")) {
            String[] parts = msgFromClient.split("_");
            String nick = parts[1];
            for (ClientHandler cl: clients) {
                if (nick.equalsIgnoreCase(cl.getName())) {
                    cl.sendMessege(name + " : " + filter.filter(parts[3]));
                    return;
                }
            }
            for (ClientHandler cl: clients) {
                if (parts[2].equalsIgnoreCase(cl.getName())) {
                    cl.sendMessege(parts[1] + "отсутсвует в чате");
                    return;
                }
            }
        } else sendMsgToAll(name + " : " + filter.filter(msgFromClient));
    }
}