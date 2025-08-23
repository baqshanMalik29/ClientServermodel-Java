import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.net.UnknownHostException;

public class client {
    public static void main(String[] args) {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter =  null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            socket = new Socket("Localhost", 1234 );
            //local host is like the domain name for ex. google.com, local host takes u to ur computer
            //This is known as loopback address, local host is resolved as ip address 127.00.0.1
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            Scanner sc = new Scanner(System.in);

            while (true){ // so it just keeps on going
                String msg = sc.nextLine();
                bufferedWriter.write(msg);
                //the writer method of buffered writer writes the given argument, string in this case
                //to the underlying writer which writes it to the underlying output stream,
                //here that is the outputStreamWriter and outputStream respectively
                bufferedWriter.newLine();
                bufferedWriter.flush();

                System.out.println("Server replied: " + bufferedReader.readLine());
                if( msg.equalsIgnoreCase("Exit")){
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
                if (inputStreamReader != null)
                    inputStreamReader.close();
                if (outputStreamWriter != null)
                    outputStreamWriter.close();
                if (bufferedReader != null)
                    bufferedReader.close();
                if (bufferedWriter != null)
                    bufferedWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }






    }
}


