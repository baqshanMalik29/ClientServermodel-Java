import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Scanner sc;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.sc = new Scanner(System.in);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msgfromClient = bufferedReader.readLine();

                if (msgfromClient == null) break; // client disconnected
                System.out.println("Message from client: " + msgfromClient);

                // Reverse string
                String reverse = new StringBuilder(msgfromClient).reverse().toString();

                // Get current time
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String currentTime = now.format(formatter);

                String serverResponse;

                if (msgfromClient.equalsIgnoreCase("Whats the time")) {
                    serverResponse = currentTime;
                    bufferedWriter.write(serverResponse);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } else if (msgfromClient.equalsIgnoreCase("Reverse")) {
                    bufferedWriter.write("Reversed: " + reverse);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } else if (msgfromClient.equalsIgnoreCase("Exit")) {
                    bufferedWriter.write("Goodbye!");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    break;
                } else {
                    // Default echo / manual input response
                    System.out.print("Enter server response: ");
                    serverResponse = sc.nextLine();
                    bufferedWriter.write(serverResponse);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        } finally {
            try {
                socket.close();
                bufferedReader.close();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class MultiThreadedServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server started on port 1234...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New client connected!");
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.start(); // each client handled in new thread
        }
    }
}
