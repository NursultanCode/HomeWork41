package BonusServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BonusServer {
    private final int port;
    Printable printable;
    Map<String,Printable> actions;

    private BonusServer(int port) {
        this.port = port;
        setMap();
    }

    private void setMap() {
        actions = new HashMap<>();
        actions.put("date",new DateReturn());
        actions.put("time", new TimeReturn());
        actions.put("reverse", new ReverseReturn());
        actions.put("upper", new UpperReturn());
        actions.put("bye", new ByeReturn());
        actions.put("normal", new NormalReturn());
    }

    public static BonusServer bindToPort(int port) {
        return new BonusServer(port);
    }

    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            try(Socket socket =  server.accept()) {
                handle(socket);
            }
        } catch (IOException e) {
            String formatMsg = "Вероятнее всего порт %s занят.%n";
            System.out.printf(formatMsg, port);
            e.printStackTrace();
        }
    }

    private void handle(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        final InputStreamReader inputStreamReader = new InputStreamReader(input, "UTF-8");
        Writer writer = new PrintWriter(output);
        try (Scanner scanner = new Scanner(inputStreamReader);) {
            while (true) {
                String message2 = scanner.nextLine().trim();
                System.out.printf("Client: %s%n",message2);
                if ("bye".equals(message2.toLowerCase())) {
                    return;
                }
                String action = check(message2);
                setPrintable(action);
                writer.write(printable.action(message2));
                writer.write(System.lineSeparator());
                writer.flush();
            }
        }
        catch (NoSuchElementException ex) {
            System.out.printf("Client dropped the connection!");
        }
    }

    private String check(String message2) {
        if (message2.startsWith("date")) return "date";
        else if (message2.startsWith("time")) return "time";
        else if (message2.startsWith("reverse")) return "reverse";
        else if (message2.startsWith("upper")) return "upper";
        else if (message2.startsWith("bye"))return "bye";
        else return "normal";
    }

    private void setPrintable(String action) {
        printable = actions.get(action);
    }
}
