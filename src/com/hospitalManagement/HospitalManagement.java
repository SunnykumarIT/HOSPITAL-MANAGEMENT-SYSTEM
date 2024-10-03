package com.hospitalManagement;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagement {
   private static final String url="jdbc:mysql://localhost:3306/hospital_db";
    private static final String user="root";
    private static final String password ="sunny@123";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver loaded successfully.....");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("connection successfully.....");
            Scanner scanner = new Scanner(System.in);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            Appointment appointment = new Appointment(connection);
            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. add patient");
                System.out.println("2. view patient");
                System.out.println("3. view doctor");
                System.out.println("4. book appointment");
                System.out.println("5. view appointment");
                System.out.println("6. exit");
                System.out.println("enter your choice");
                int choice=scanner.nextInt();
                switch (choice){
                    case 1:
                        patient.addPatient();
                        break;
                    case 2:
                        patient.viewPatient();
                        break;
                    case 3:
                        doctor.viewDoctor();
                        break;
                    case 4:
                        appointment.bookAppointment(connection,scanner,patient,doctor);
                        break;
                    case 5:
                        appointment.viewAppointment();
                        break;
                    case 6:
                        System.out.println("THANKS FOR USING HOSPITAL MANAGEMENT.... ");
//                        System.exit(0);
                        return;
                }

            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
