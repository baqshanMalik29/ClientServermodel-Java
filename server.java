import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class server {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        ServerSocket serverSocket = null;

        serverSocket = new ServerSocket(1234);

        while (true) {
            try {

                socket = serverSocket.accept();
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                Scanner sc = new Scanner(System.in);

                while (true){
                    String msgforClient = bufferedReader.readLine();
                    System.out.println("msg from client: " + msgforClient);

                    String serverResponse = sc.nextLine();
                    bufferedWriter.write(serverResponse);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if (msgforClient.equalsIgnoreCase("Exit"))
                    break;



                }
                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedWriter.close();
                bufferedReader.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    }