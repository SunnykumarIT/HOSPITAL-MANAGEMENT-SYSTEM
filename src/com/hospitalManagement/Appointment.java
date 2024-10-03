package com.hospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Appointment {
    private Connection connection;
    public Appointment(Connection connection) {
        this.connection = connection;

    }
    public void viewAppointment(){
        String query="select * from appointments";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int appointment_id=rs.getInt("id");
                int patient_id=rs.getInt("patient_id");
                int doctor_id = rs.getInt("doctor_id");
                String date=rs.getString("appointment_date");
                System.out.println("| appointment_id: "+appointment_id+" | patient_id: "+patient_id+" | doctor_id: "+doctor_id+" | date:"+date+" |");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    private  boolean checkDoctorAvailable(Connection connection,String date,int d_id){
        String query="select * from appointments where doctor_id=? and appointment_date=?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,d_id);
            preparedStatement.setString(2,date);
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    public  void bookAppointment(Connection connection, Scanner scanner, Patient patient, Doctor doctor){
        System.out.println("enter patient id");
        int p_id=scanner.nextInt();
        System.out.println("enter doctor id");
        int d_id =scanner.nextInt();
        System.out.println("enter date in this format(yyyy-mm-dd)");
        String date=scanner.next();
        String query="Insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
        if(patient.getPatientById(p_id) && doctor.getDoctorById(d_id)){
            if(!checkDoctorAvailable(connection,date,d_id)){
                try {
                    PreparedStatement preparedStatement=connection.prepareStatement(query);
                    preparedStatement.setInt(1,p_id);
                    preparedStatement.setInt(2,d_id);
                    preparedStatement.setString(3,date);
                    int affectedRow = preparedStatement.executeUpdate();
                    if(affectedRow>0){
                        System.out.println("booking appointment successful");
                    }
                    else {
                        System.out.println("booking not successful");
                    }

                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            }else{
                System.out.println("doctor is not available");
            }
        }else{
            System.out.println("either patient or doctor is not available");
        }
    }
}
