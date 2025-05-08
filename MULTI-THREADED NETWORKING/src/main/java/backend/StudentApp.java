package backend;

import backend.resources.StudentController;
import backend.student.repo.StudentRepoImpl;
import backend.student.service.StudentServiceImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class StudentApp {
    public static void run(){
        try(ServerSocket server = new ServerSocket(5898)){
            server.setSoTimeout((1000 * 60) * 10);
            System.out.println("Server started successfully, listening on port : " + server.getLocalPort());
            while(true){
                System.out.println("Waiting for incoming client request...");
                Socket acceptedConnection = server.accept();

                System.out.println("Connection received, spawning servicing thread for machine at : " + acceptedConnection.getInetAddress().getHostAddress() + ":" + acceptedConnection.getPort());
                new ServiceThread(acceptedConnection);
            }
        }catch (SocketTimeoutException e){
            System.out.println("Request Timed Out, crashing server...");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        run();
    }
}

class ServiceThread implements Runnable{
    private Socket socket;
    private StudentController studentController;

    public ServiceThread(Socket socket){
        this.socket = socket;
        this.studentController = new StudentController(new StudentServiceImpl(new StudentRepoImpl()));
        new Thread(this).start();
    }

    @Override
    public void run() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()))){

            String requestString = reader.readLine();
            String response = this.studentController.processRequest(requestString);
            writer.println(response);
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}