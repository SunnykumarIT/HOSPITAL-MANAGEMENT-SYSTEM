package com.hospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    Patient(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }

    public void addPatient(){
        System.out.println("enter your name:");
        String name = scanner.next();
        scanner.nextLine();
        System.out.println("enter your age");
        int age=scanner.nextInt();
        System.out.println("enter your gender");
        String gender=scanner.next();

        try {
            String query ="insert into patients(name,age,gender) values(?,?,?)";
           PreparedStatement preparedStatement= connection.prepareStatement(query);
           preparedStatement.setString(1,name);
           preparedStatement.setInt(2,age);
           preparedStatement.setString(3,gender);
           int rowAffected = preparedStatement.executeUpdate();
           if(rowAffected>0){
               System.out.println("Patient added successful......");
           }else{
               System.out.println("Patient added failure......");
           }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

   public void viewPatient(){
        String query="select * from patients";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery();
             while(rs.next()){
                 int id=rs.getInt("id");
                 String name=rs.getString("name");
                 int age=rs.getInt("age");
                 String gender = rs.getString("gender");
                 System.out.println("| id: "+id+" | name: "+name+" | age: "+age+" | gender: "+gender+" |");
             }
        }catch (SQLException e){
       System.out.println(e.getMessage());
       e.printStackTrace();
   }
    }
    public boolean getPatientById(int id){
        String query ="select * from patients where id=?";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return true;
            }
            else{
                return false;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
