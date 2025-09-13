import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
//                LocalDateTime now = LocalDateTime.now();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//                String currentTime = now.format(formatter);

                while (true){
                    String msgfromClient = bufferedReader.readLine();
                    System.out.println("msg from client: " + msgfromClient);
                    String[] strNums = msgfromClient.split(",");
                    int[] arr = new int[strNums.length];
                    for (int i = 0; i < strNums.length; i++) {
                        arr[i] = Integer.parseInt(strNums[i]);
                    }
                    int n = arr.length;
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = 0; j < n - 1 - i; j++) {
                            if (arr[j] > arr[j + 1]) {
                                // Swap elements
                                int temp = arr[j];
                                arr[j] = arr[j + 1];
                                arr[j + 1] = temp;
                            }
                        }
                    }
                    int[] serverResponse = arr;
                    bufferedWriter.write(Arrays.toString(serverResponse));
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
//                    String reverse = new StringBuilder(msgfromClient).reverse().toString();
//                    if (msgfromClient.equalsIgnoreCase("Whats the time"))
//                        serverResponse = currentTime;
//                        bufferedWriter.write(serverResponse);
//                        bufferedWriter.newLine();
//                        bufferedWriter.flush();
//
//                    if (msgfromClient.equalsIgnoreCase("Reverse"))
//                        bufferedWriter.write(reverse);
//                        bufferedWriter.newLine();
//                        bufferedWriter.flush();



                    if (msgfromClient.equalsIgnoreCase("Exit"))
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