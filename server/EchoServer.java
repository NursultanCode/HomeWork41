package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoServer {
    private final int port;

    private EchoServer(int port) {
        this.port = port;
    }

    public static EchoServer bindToPort(int port) {
        return new EchoServer(port);
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
                message2 = reverse(message2);
                writer.write(message2);
                writer.write(System.lineSeparator());
                writer.flush();
            }
        }
        catch (NoSuchElementException ex) {
            System.out.printf("Client dropped the connection!");
        }
    }

    private String reverse(String input) {

        StringBuilder input1 = new StringBuilder();
        input1.append(input);
        input1.reverse();
        return input1.toString();
    }
}
