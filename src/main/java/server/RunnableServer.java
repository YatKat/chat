package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RunnableServer implements Runnable {

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public RunnableServer(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        String entry;
        try {
            while (!socket.isClosed()){
                entry = inputStream.readUTF();
                if(entry.equalsIgnoreCase("stop")){
                    System.out.println("Socket is stopped");
                    break;
                }
                outputStream.writeUTF(entry);
                outputStream.flush();
            }
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
