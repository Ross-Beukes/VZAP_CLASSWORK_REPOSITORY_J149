package frontend;



import backend.student.model.Student;
import backend.student.repo.StudentRepoImpl;
import backend.student.service.StudentService;
import backend.student.service.StudentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class View {
    private Socket socket;
    Scanner input = new Scanner(System.in);
    private final ObjectMapper mapper = new ObjectMapper();

    public View(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()))
        ) {
            while (true) {
                menu();
                System.out.println("Selection : ");
                String selection = input.nextLine();

                switch (selection) {
                    case "1": {
                        writer.println(createStudent());
                        writer.flush();
                        String[] response = reader.readLine().split(";");
                        if(response[0].equalsIgnoreCase("1")){
                            System.out.println("Student Created Successfully : \n");
                            System.out.println(this.mapper.readValue(response[1], Student.class));
                        }else{
                            System.out.println("Error!!\n");
                            System.out.println(response[1]);
                        }
                        break;
                    }
                    case "2": {
                        return;
                    }
                    default: {
                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String createStudent() throws JsonProcessingException {
        Student student = new Student();

        System.out.println("First name : ");
        student.setFirstName(input.nextLine());
        System.out.println("Surname : ");
        student.setSurname(input.nextLine());
        System.out.println("Email Address : ");
        student.setEmailAddress(input.nextLine());
        System.out.println("ID Number : ");
        student.setIdNumber(input.nextLine());

        return "1" + ";" + mapper.writeValueAsString(student);
    }

    public void menu(){
        System.out.println("""
                1. Create Student
                2. Exit
                """);
    }

    public static void main(String[] args) {
        try {
            new View(new Socket("localhost", 5898)).run();
        } catch (IOException e) {
            System.out.println("Could not connect to server");
            e.printStackTrace();
        }
    }
}
