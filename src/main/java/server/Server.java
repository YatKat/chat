package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ExecutorService executorService = Executors.newFixedThreadPool(4);
    static List<RunnableServer> clientList = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9090);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            while(!server.isClosed()){
                if(br.ready()){
                    String serverCommand = br.readLine();
                    if(serverCommand.equalsIgnoreCase("stop")){
                        server.close();
                        System.out.println("Main server exit");
                        break;
                    }
                }
                Socket socket = server.accept();
                System.out.print("Connection accepted.");
                RunnableServer runnableServer = new RunnableServer(socket);
                clientList.add(runnableServer);
                for (RunnableServer rs : clientList) {
                    executorService.execute(rs);
                }
            }
            executorService.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
