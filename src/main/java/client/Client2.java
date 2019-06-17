package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client2 {
    public static void main(String[] args) throws InterruptedException {
        try(Socket socket = new Socket("localhost", 9090)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            System.out.println("Client connected to socket.");

            while (!socket.isOutputShutdown()){
                if(bufferedReader.ready()){
                    Thread.sleep(1000);
                    String clientText = bufferedReader.readLine();
                    outputStream.writeUTF("Client writes: " + clientText);
                    outputStream.flush();
                    System.out.println("Clien sent message " + clientText + " to server.");
                    Thread.sleep(1000);
                    if (clientText.equalsIgnoreCase("stop")){
                        System.out.println("Client stopped connection");
                        if(inputStream.read() > -1){
                            String input = inputStream.readUTF();
                            System.out.println(input);
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
