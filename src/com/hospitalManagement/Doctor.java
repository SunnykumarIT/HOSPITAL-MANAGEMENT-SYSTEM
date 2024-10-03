package com.hospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;


    public Doctor(Connection connection) {
        this.connection = connection;

    }
    public void viewDoctor(){
        String query="select * from doctors";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String specialization = rs.getString("specialization");
                System.out.println("| id: "+id+" | name: "+name+" | specialization: "+specialization+" |");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public boolean getDoctorById(int id){
        String query ="select * from doctors where id=?";
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
