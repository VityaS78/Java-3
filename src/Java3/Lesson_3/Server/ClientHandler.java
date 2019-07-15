package Java3.Lesson_3.Server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private String name;

    public String getName() {
        return name;
    }

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
//            new Thread(() -> {
//                try {
//                    authentication();
//                    readMessege();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    closeConnection();
//                }
//            }).start();
            run();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка создания обработчика");
        }
    }

    private void authentication() throws IOException {
        while (true) {
            String msg = in.readUTF();
            if (msg.startsWith("/auth")) {
                String[] parts = msg.split("_");
                String nick = SQLAuth.getNick(new String[]{parts[1], parts[2]});
                if (nick != null) {
                    if (!server.isNickBusy(nick)) {
                        name = nick;
                        sendMessege("/authok_" + nick);
                        server.addClient(this);
                        server.sendMsgToAll(name + " зашёл в чат");
                        return;
                    } else {
                        sendMessege("/authUse_");
                        return;
                    }
                } else {
                    sendMessege("/authWrong");
                    return;
                }
            }
        }
    }

    public void readMessege() throws IOException {
        while (true) {
            String strFromClient = in.readUTF();
            System.out.println("Сщщбщение от " + name + " : " + strFromClient);
            if ("EXIT".equalsIgnoreCase(strFromClient)) {
                return;
            }
            server.distributeMsg(strFromClient, name);
        }
    }

    private void closeConnection() {
        server.removeClient(this);
        server.sendMsgToAll(name + " покинул чат");
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public void sendMessege(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            authentication();
            readMessege();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}
