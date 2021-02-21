import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer {
    private static final int PORT = 8787;
    private static ServerSocket server;
    private static Socket socket;

    public static void main(String[] args) throws IOException {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started...");
            socket = server.accept();
            System.out.println("Client connected.");

            Scanner scanner = new Scanner(socket.getInputStream());
            Scanner response = new Scanner(System.in);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            new Thread(() -> {
                while (true) {
                    String str = scanner.nextLine();
                    if (str.equals("/end")) {
                        System.out.println("Client disconnected!");
                        break;
                    }
                    System.out.println("Client: " + str);
                }
            }).start();

            while (true) {
                out.println(response.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
