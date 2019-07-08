package Java3.Lesson_3.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class ClientWindow extends JFrame {
    private static final int COUNT = 10;
    private JTextField clientMsg;
    private JTextField toNick;
    private JTextArea serverMsg;
    private JPanel authPanel;

    final String serverHost;
    final int serverPort;

    Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private FileOutputStream fileOut;
    private FileInputStream fileIn;
    private BufferedReader rd;

    private String name;
    private ArrayList<String> strings = new ArrayList<>();

    File file;

    public ClientWindow(String host, int port) {
        serverHost = host;
        serverPort = port;
        this.name = "";
        initConnection();
        initServerListner();
        initGUI();
    }


    private void initConnection() {
        try {
            socket = new Socket(serverHost, serverPort);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    private void initGUI() {
        setBounds(600, 300, 500, 500);
        setTitle("Чат");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        authPanel = new JPanel(new GridLayout());
        JTextField jtfLogin = new JTextField();
        JTextField jtfPasseord = new JTextField();
        JButton jbAuth = new JButton("Auth");
        authPanel.add(jtfLogin);
        authPanel.add(jtfPasseord);
        authPanel.add(jbAuth);
        add(authPanel, BorderLayout.NORTH);
        jbAuth.addActionListener(e -> {
            try {
                sendAuthCommand(jtfLogin.getText() + "_" + jtfPasseord.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        serverMsg = new JTextArea();
        serverMsg.setEditable(false);
        serverMsg.setLineWrap(true);
        JScrollPane sp = new JScrollPane(serverMsg);
        add(sp, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);

        JButton sendButton = new JButton("Отправить");
        bottomPanel.add(sendButton, BorderLayout.EAST);
        clientMsg = new JTextField();
        bottomPanel.add(clientMsg, BorderLayout.CENTER);

        JPanel toNickPanel = new JPanel(new BorderLayout());
        toNick = new JTextField();
        JLabel label = new JLabel("Сообщение для клиента: ");
        toNickPanel.add(label, BorderLayout.WEST);
        bottomPanel.add(toNickPanel, BorderLayout.NORTH);
        toNickPanel.add(toNick, BorderLayout.CENTER);

        sendButton.addActionListener(e -> {
            String msg;
            if (!toNick.getText().trim().isEmpty()) {
                msg = "/ToNick_" + toNick.getText().trim() + "_" + getName() + "_" + clientMsg.getText();
                toNick.setText("");
            } else {
                msg = clientMsg.getText();
            }
            try {
                sendMessege(msg);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    out.writeUTF("EXIT");
                    out.flush();
                    socket.close();
                    out.close();
                    in.close();
                    fileOut.close();
                    fileIn.close();

                } catch (IOException e1) {
                    System.err.println(e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    private void sendMessege(String msg) throws IOException {
        if (!msg.trim().isEmpty()) {
            out.writeUTF(msg);
            out.flush();
            clientMsg.setText("");
        }
    }

    void sendAuthCommand(String str) throws IOException {
        String command = "/auth_" + str;
//        name = nick;
        out.writeUTF(command);
        out.flush();
    }

    private void readMessagesFromFile(int cout) {
        String tmp;
        try {
            while ((tmp = rd.readLine()) != null) {
                strings.add(tmp);
                if (strings.size() == cout + 1) {
                    strings.remove(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (strings.size() != 0) {
            serverMsg.append(strings.remove(0) + "\n");
        }
    }

    private void initServerListner() {
        new Thread(() -> {
            try {
                while (true) {
                    String msg = in.readUTF();
                    System.out.println(msg);
                    if (msg.startsWith("/authok")) {
                        String[] authParts = msg.split("_");
                        name = authParts[1];
                        authPanel.setVisible(false);
                        try {
                            file = new File("history_" + name + ".txt");
                            fileOut = new FileOutputStream(file, true);
                            fileIn = new FileInputStream(file);
                            rd = new BufferedReader(new InputStreamReader(fileIn));
                            readMessagesFromFile(COUNT);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        String str = "Добро пожаловать в чат. \n";
                        serverMsg.append(str);
                        fileOut.write(str.getBytes());
                    } else if (msg.equals("/authUse_")) {
                        serverMsg.append("Учётная запись уже используется. \n");
                    } else if (msg.equals("/authWrong")) {
                        serverMsg.append("Ошибка авторизации.");
                    } else {
                        msg = msg + "\n";
                        serverMsg.append(msg);
                        fileOut.write(msg.getBytes());
                        fileOut.flush();
                    }

                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
}
