
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {
    private static Socket socket;

    public static void main(String[] args) {

        try {
            socket = new Socket("localhost", 8787);
            System.out.println("Client started...");

            Scanner scanner = new Scanner(socket.getInputStream());
            Scanner respond = new Scanner(System.in);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            new Thread(()->{

            while (true) {
                String in = scanner.nextLine();
                System.out.println("Server: " + in);
                if (in.equals("/end")) {
                    System.out.println("Client disconnected!");
                    break;
                }
            }

            }).start();

            while (true){
                out.println(respond.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
