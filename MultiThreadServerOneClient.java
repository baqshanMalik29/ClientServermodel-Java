import java.io.*;
import java.net.*;
import java.util.Scanner;
//practise class extends thread class(inheritance)
class MultiThreadServerOneClient extends Thread{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Scanner sc;
    //constructor
    public MultiThreadServerOneClient(Socket socket) throws IOException{
        this.socket =  socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.sc = new Scanner(System.in);

    }
    public void run(){
        try {
            while (true){
                String msgfromclient = bufferedReader.readLine();
                System.out.println("The client says: " + msgfromclient);
                if (msgfromclient.equalsIgnoreCase("Exit"))
                    break;
                System.out.print("Enter server response: ");
                String serverresponse = sc.nextLine();
                bufferedWriter.write(serverresponse);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                socket.close();
                bufferedWriter.close();
                bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        while (true){
            Socket socket = serverSocket.accept();
            MultiThreadServerOneClient MultiThreadServerOneClient = new MultiThreadServerOneClient(socket);
            MultiThreadServerOneClient.start();
            try {
                MultiThreadServerOneClient.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);// wait until this client thread finishes(this is for one client at a time)
            }

        }

        }

    }

